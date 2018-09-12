package digital.sogood.livestreamfails.mobile.inject.module

import android.content.Context
import dagger.Module
import dagger.Provides
import digital.sogood.livestreamfails.data.executor.JobExecutor
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.mobile.App
import digital.sogood.livestreamfails.mobile.UiThread
import digital.sogood.livestreamfails.mobile.inject.scope.PerApplication

/**
 * Module used to provide dependencies at an application-level.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
open class ApplicationModule {
    @Provides
    @PerApplication
    fun provideContext(app: App): Context = app

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread
}