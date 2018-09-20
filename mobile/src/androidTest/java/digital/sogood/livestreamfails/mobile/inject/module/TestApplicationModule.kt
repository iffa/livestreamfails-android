package digital.sogood.livestreamfails.mobile.inject.module

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class TestApplicationModule {
    @Binds
    abstract fun bindContext(application: Application): Context
}