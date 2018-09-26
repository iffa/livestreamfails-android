package digital.sogood.livestreamfails.presentation.util

import androidx.test.espresso.IdlingResource
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class SimpleCountingIdlingResourceTest {
    private lateinit var idlingResource: SimpleCountingIdlingResource
    private lateinit var callback: IdlingResource.ResourceCallback
    private val resourceName = "ResourceName"

    @Before
    fun setUp() {
        idlingResource = SimpleCountingIdlingResource(resourceName)
        callback = mock()
    }

    @Test
    fun getNameTest() {
        assertEquals(resourceName, idlingResource.name)
    }

    @Test
    fun isIdleNowTest() {
        assertEquals(true, idlingResource.isIdleNow)

        idlingResource.increment()

        assertEquals(false, idlingResource.isIdleNow)
    }

    @Test
    fun registerIdleTransitionCallbackTest() {
        idlingResource.registerIdleTransitionCallback(callback)

        assertEquals(callback, idlingResource.resourceCallback)
    }

    @Test
    fun decrementToZeroTest() {
        idlingResource.registerIdleTransitionCallback(callback)

        idlingResource.increment()
        idlingResource.decrement()

        verify(callback).onTransitionToIdle()
    }

    @Test(expected = IllegalArgumentException::class)
    fun decrementBelowZeroThrowsException() {
        idlingResource.registerIdleTransitionCallback(callback)

        idlingResource.decrement()
    }
}