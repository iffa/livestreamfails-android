package digital.sogood.livestreamfails.mobile.inject.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * Module used to provide dependencies at an application-level.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class ApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}