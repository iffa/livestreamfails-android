package digital.sogood.livestreamfails.mobile.inject

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import digital.sogood.livestreamfails.mobile.App
import digital.sogood.livestreamfails.mobile.inject.module.ApplicationModule
import digital.sogood.livestreamfails.mobile.inject.scope.PerApplication

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@PerApplication
@Component(modules = [ApplicationModule::class, AndroidSupportInjectionModule::class])
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: App)
}