package digital.sogood.livestreamfails.mobile.inject.component

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import digital.sogood.livestreamfails.mobile.FailsApplication
import digital.sogood.livestreamfails.mobile.inject.module.*
import javax.inject.Singleton

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Singleton
@Component(modules = [
    ApplicationModule::class,
    AndroidSupportInjectionModule::class,
    DomainModule::class,
    DataModule::class,
    PresentationModule::class,
    RemoteModule::class,
    UiModule::class
])
interface ApplicationComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: FailsApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(application: FailsApplication)
}