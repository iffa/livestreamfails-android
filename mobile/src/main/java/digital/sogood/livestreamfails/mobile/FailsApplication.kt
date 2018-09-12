package digital.sogood.livestreamfails.mobile

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.mobile.inject.component.DaggerApplicationComponent
import timber.log.Timber

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailsApplication : DaggerApplication() {
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

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerApplicationComponent.builder().application(this).build()
    }
}