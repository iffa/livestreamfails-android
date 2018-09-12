package digital.sogood.livestreamfails.mobile.test

import androidx.test.InstrumentationRegistry
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import digital.sogood.livestreamfails.mobile.inject.component.DaggerTestApplicationComponent
import digital.sogood.livestreamfails.mobile.inject.component.TestApplicationComponent

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class TestApplication : DaggerApplication() {
    private lateinit var appComponent: TestApplicationComponent

    companion object {
        fun appComponent(): TestApplicationComponent {
            return (InstrumentationRegistry.getTargetContext().applicationContext as TestApplication).appComponent
        }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerTestApplicationComponent.builder().application(this).build()

        return appComponent
    }
}