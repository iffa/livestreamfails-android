package digital.sogood.livestreamfails.mobile.inject.module

import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.mobile.executor.UiThread
import digital.sogood.livestreamfails.mobile.ui.fail.FailFragment
import digital.sogood.livestreamfails.mobile.ui.main.MainActivity
import digital.sogood.livestreamfails.mobile.ui.streamer.StreamerActivity

/**
 * Module that provides all dependencies from the UI layer.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class UiModule {
    @Binds
    abstract fun bindPostExecutionThread(uiThread: UiThread): PostExecutionThread

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeStreamerActivity(): StreamerActivity

    @ContributesAndroidInjector
    abstract fun contributeFailFragment(): FailFragment
}