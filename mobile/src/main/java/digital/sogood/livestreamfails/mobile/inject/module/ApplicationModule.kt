package digital.sogood.livestreamfails.mobile.inject.module

import android.content.Context
import dagger.Binds
import dagger.Module
import digital.sogood.livestreamfails.mobile.AndroidSettingsService
import digital.sogood.livestreamfails.mobile.FailsApplication
import digital.sogood.livestreamfails.presentation.SettingsService

/**
 * Module used to provide dependencies at an application-level.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: FailsApplication): Context

    @Binds
    abstract fun bindSettings(androidSettings: AndroidSettingsService): SettingsService
}