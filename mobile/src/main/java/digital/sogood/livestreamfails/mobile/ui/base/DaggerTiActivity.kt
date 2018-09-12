package digital.sogood.livestreamfails.mobile.ui.base

import android.content.res.Configuration
import android.os.Bundle
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.HasSupportFragmentInjector
import net.grandcentrix.thirtyinch.*
import net.grandcentrix.thirtyinch.internal.*
import net.grandcentrix.thirtyinch.util.AnnotationUtil
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * [AppCompatActivity] that implements the functionality of [TiActivity] and [DaggerAppCompatActivity],
 * allowing usage of both at the same time.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
abstract class DaggerTiActivity<P : TiPresenter<V>, V : TiView> : AppCompatActivity(),
        TiPresenterProvider<P>, TiViewProvider<V>, DelegatedTiActivity,
        TiLoggingTagProvider, InterceptableViewBinder<V>, PresenterAccessor<P, V>,
        HasFragmentInjector, HasSupportFragmentInjector {
    private val delegate = TiActivityDelegate(this, this, this, this, PresenterSavior.getInstance())

    private val uiThreadExecutor = UiThreadExecutor()

    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>

    @Suppress("DEPRECATION")
    @Inject
    lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    override fun supportFragmentInjector() = supportFragmentInjector

    override fun fragmentInjector() = frameworkFragmentInjector

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        delegate.onCreate_afterSuper(savedInstanceState)
    }

    @CallSuper
    override fun onStart() {
        super.onStart()

        delegate.onStart_afterSuper()
    }

    @CallSuper
    override fun onStop() {
        delegate.onStop_beforeSuper()

        super.onStop()

        delegate.onStop_afterSuper()
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        delegate.onSaveInstanceState_afterSuper(outState)
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        delegate.onDestroy_afterSuper()
    }

    override fun addBindViewInterceptor(interceptor: BindViewInterceptor): Removable {
        return delegate.addBindViewInterceptor(interceptor)
    }

    override fun getHostingContainer(): Any = this

    override fun getInterceptedViewOf(interceptor: BindViewInterceptor): V? {
        return delegate.getInterceptedViewOf(interceptor)
    }

    override fun getInterceptors(predicate: InterceptableViewBinder.Filter<BindViewInterceptor>): MutableList<BindViewInterceptor> {
        return delegate.getInterceptors(predicate)
    }

    override fun getLoggingTag(): String {
        return (this.javaClass.simpleName
                + ":" + DaggerTiActivity::class.java.simpleName
                + "@" + Integer.toHexString(this.hashCode()))
    }

    override fun getPresenter(): P = delegate.presenter

    override fun getUiThreadExecutor(): Executor = uiThreadExecutor

    override fun invalidateView() {
        delegate.invalidateView()
    }

    override fun isActivityFinishing(): Boolean {
        return isFinishing
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)

        delegate.onConfigurationChanged_afterSuper(newConfig)
    }

    @Suppress("UNCHECKED_CAST")
    override fun provideView(): V {
        val foundViewInterface = AnnotationUtil
                .getInterfaceOfClassExtendingGivenInterface(this.javaClass, TiView::class.java)

        return if (foundViewInterface == null) {
            throw IllegalArgumentException(
                    "This Activity doesn't implement a TiView interface. " + "This is the default behaviour. Override provideView() to explicitly change this.")
        } else {
            if (foundViewInterface.simpleName == "TiView") {
                throw IllegalArgumentException(
                        "extending TiView doesn't make sense, it's an empty interface." + " This is the default behaviour. Override provideView() to explicitly change this.")
            } else {
                // assume that the activity itself is the view and implements the TiView interface
                this as V
            }
        }
    }
}