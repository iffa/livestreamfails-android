package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Test for the implementation of [FailService]. Note that this test requires internet access,
 * as functionality is verified by actually calling the API and trying to parse it successfully.
 *
 * The test loads fails with "front page" parameters, [TimeFrame.ALL_TIME] and [Order.TOP] for
 * consistent results.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailServiceImplTest {
    private lateinit var service: FailService

    @Before
    fun setUp() {
        service = FailServiceImpl(OkHttpClient())
    }

    @Test
    fun getFailsCompletes() {
        service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, null, null)
                .test()
                .assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val observer = service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, null, null)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getFailsReturnsEmpty() {
        val observer = service.getFails(Int.MAX_VALUE, TimeFrame.ALL_TIME, Order.TOP, false, null, null)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isEmpty(), "Model list is not empty")
    }
}