package digital.sogood.livestreamfails.mobile

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.mobile.inject.DaggerApplicationComponent
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class App : Application(), HasActivityInjector {
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerApplicationComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }
}