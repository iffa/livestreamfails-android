package digital.sogood.livestreamfails.mobile.inject.component

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import digital.sogood.livestreamfails.mobile.inject.module.*
import javax.inject.Singleton

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Singleton
@Component(modules = [
    TestApplicationModule::class,
    AndroidSupportInjectionModule::class,
    DomainModule::class,
    TestDataModule::class,
    TestRemoteModule::class,
    PresentationModule::class,
    UiModule::class
])
interface TestApplicationComponent : ApplicationComponent {
    fun failRepository(): FailRepository

    fun detailsRepository(): DetailsRepository

    fun gameRepository(): GameRepository

    fun streamerRepository(): StreamerRepository

    fun postExecutionThread(): PostExecutionThread

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }
}