package digital.sogood.livestreamfails.presentation.util

import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class SimpleCountingIdlingResource(private val resourceName: String) : IdlingResource {
    private val counter = AtomicInteger(0)

    @Volatile
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean = counter.get() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    fun increment() {
        counter.getAndIncrement()
    }

    fun decrement() {
        val value = counter.decrementAndGet()
        if (value == 0) {
            resourceCallback?.onTransitionToIdle()
        }

        if (value < 0) {
            throw IllegalArgumentException("Counter reached below 0")
        }
    }
}