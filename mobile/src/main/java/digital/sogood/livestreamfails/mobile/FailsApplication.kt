package digital.sogood.livestreamfails.mobile

import androidx.appcompat.app.AppCompatDelegate
import com.squareup.leakcanary.LeakCanary
import dagger.android.DaggerApplication
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.mobile.inject.component.DaggerApplicationComponent
import digital.sogood.livestreamfails.presentation.SettingsService
import timber.log.Timber
import javax.inject.Inject

/**
 * TODO: Apply selected night mode as soon as the setting has been changed
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailsApplication : DaggerApplication() {
    private val injector = DaggerApplicationComponent.builder()
            .application(this)
            .build()

    @Inject
    lateinit var settings: SettingsService

    override fun onCreate() {
        super.onCreate()

        if (LeakCanary.isInAnalyzerProcess(this)) return

        LeakCanary.install(this)

        injector.inject(this)

        setupTimber()

        setupNightMode()
    }

    private fun setupNightMode() {
        when (settings.isNightModeEnabled()) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    override fun applicationInjector() = injector
}