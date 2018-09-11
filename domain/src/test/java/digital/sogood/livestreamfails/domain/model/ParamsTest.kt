package digital.sogood.livestreamfails.domain.model

import org.junit.Test
import kotlin.test.assertEquals

/**
 * This is ridiculous, but gotta have that coverage.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class ParamsTest {
    @Test
    fun timeFrameTest() {
        val a = TimeFrame.ALL_TIME

        assertEquals("all", a.value)
    }

    @Test
    fun orderTest() {
        val a = Order.TOP

        assertEquals("top", a.value)
    }
}