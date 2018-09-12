package digital.sogood.livestreamfails.mobile.inject

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.mobile.inject.scope.PerApplication
import digital.sogood.livestreamfails.mobile.module.TestApplicationModule
import digital.sogood.livestreamfails.mobile.test.TestApplication

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@PerApplication
@Component(modules = [TestApplicationModule::class, AndroidSupportInjectionModule::class])
interface TestApplicationComponent : ApplicationComponent {
    fun postExecutionThread(): PostExecutionThread

    fun inject(application: TestApplication)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): TestApplicationComponent.Builder

        fun build(): TestApplicationComponent
    }
}