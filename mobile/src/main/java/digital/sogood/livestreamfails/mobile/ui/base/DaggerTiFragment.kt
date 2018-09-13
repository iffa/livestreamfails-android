package digital.sogood.livestreamfails.mobile.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.app.BackstackReader
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import dagger.android.support.HasSupportFragmentInjector
import net.grandcentrix.thirtyinch.*
import net.grandcentrix.thirtyinch.internal.*
import net.grandcentrix.thirtyinch.util.AnnotationUtil
import java.util.concurrent.Executor
import javax.inject.Inject

/**
 * [Fragment] implementation with the functionality of [TiFragment] and [DaggerFragment],
 * allowing usage of both at the same time.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
abstract class DaggerTiFragment<P : TiPresenter<V>, V : TiView> : Fragment(),
        DelegatedTiFragment, TiPresenterProvider<P>, TiLoggingTagProvider,
        TiViewProvider<V>, InterceptableViewBinder<V>, PresenterAccessor<P, V>,
        HasSupportFragmentInjector {

    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    private val delegate = TiFragmentDelegate(this, this, this, this, PresenterSavior.getInstance())

    private val uiThreadExecutor = UiThreadExecutor()

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        delegate.onCreate_afterSuper(savedInstanceState)
    }

    @CallSuper
    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)

        super.onAttach(context)
    }

    @CallSuper
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        delegate.onCreateView_beforeSuper(inflater, container, savedInstanceState)

        return super.onCreateView(inflater, container, savedInstanceState)
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
    }

    @CallSuper
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        delegate.onSaveInstanceState_afterSuper(outState)
    }

    @CallSuper
    override fun onDestroyView() {
        delegate.onDestroyView_beforeSuper()

        super.onDestroyView()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()

        delegate.onDestroy_afterSuper()
    }

    override fun addBindViewInterceptor(interceptor: BindViewInterceptor): Removable {
        return delegate.addBindViewInterceptor(interceptor)
    }

    override fun getHostingContainer(): Any = requireHost()

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

    override fun getUiThreadExecutor(): Executor {
        return uiThreadExecutor
    }

    override fun invalidateView() {
        delegate.invalidateView()
    }

    override fun isFragmentAdded(): Boolean {
        return isAdded
    }

    override fun isFragmentDetached(): Boolean {
        return isDetached
    }

    override fun isFragmentInBackstack(): Boolean {
        return BackstackReader.isInBackStack(this)
    }

    override fun isFragmentRemoving(): Boolean {
        return isRemoving
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    @Suppress("UNCHECKED_CAST")
    override fun provideView(): V {
        val foundViewInterface = AnnotationUtil
                .getInterfaceOfClassExtendingGivenInterface(this.javaClass, TiView::class.java)

        return if (foundViewInterface == null) {
            throw IllegalArgumentException(
                    "This Fragment doesn't implement a TiView interface. " + "This is the default behaviour. Override provideView() to explicitly change this.")
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

    override fun setRetainInstance(retain: Boolean) {
        if (retain) {
            throw IllegalStateException("Retaining TiFragment is not allowed. "
                    + "setRetainInstance(true) should only be used for headless Fragments. "
                    + "Move your state into the TiPresenter which survives recreation of TiFragment")
        }
        super.setRetainInstance(retain)
    }
}