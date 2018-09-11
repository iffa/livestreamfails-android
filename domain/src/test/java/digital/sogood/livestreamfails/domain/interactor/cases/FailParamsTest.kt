package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me></me>@santeri.xyz>
 */
class FailParamsTest {
    @Test
    fun equalsWithDifferentPage() {
        val paramA = FailParams(0, TimeFrame.ALL_TIME, Order.RANDOM, false, "Game", "Streamer")
        val paramB = paramA.copy(page = 1)

        assertTrue(paramA.equalsIgnorePage(paramB), "Parameter instances should be identical when ignoring page")
    }

    @Test
    fun equalsWithSamePage() {
        val param = FailParams(0, TimeFrame.ALL_TIME, Order.RANDOM, false, "Game", "Streamer")

        assertTrue(param.equalsIgnorePage(param.copy()), "Parameter instances should be identical")
    }

    @Test
    fun isNotEqualIgnoringPage() {
        val param = FailParams(0, TimeFrame.ALL_TIME, Order.RANDOM, false, "Game", "Streamer")

        assertFalse(param.equalsIgnorePage(param.copy(order = Order.TOP)), "Parameter instances should not be identical")
    }
}