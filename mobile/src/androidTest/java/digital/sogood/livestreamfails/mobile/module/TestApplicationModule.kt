package digital.sogood.livestreamfails.mobile.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import digital.sogood.livestreamfails.data.executor.JobExecutor
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.mobile.UiThread
import digital.sogood.livestreamfails.mobile.inject.scope.PerApplication

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
class TestApplicationModule {
    @Provides
    @PerApplication
    fun provideContext(application: Application): Context = application

    @Provides
    @PerApplication
    internal fun provideThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor = jobExecutor

    @Provides
    @PerApplication
    internal fun providePostExecutionThread(uiThread: UiThread): PostExecutionThread = uiThread
}