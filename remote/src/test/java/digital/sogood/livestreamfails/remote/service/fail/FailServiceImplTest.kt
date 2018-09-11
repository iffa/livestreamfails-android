package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import okhttp3.OkHttpClient
import org.apache.http.client.utils.URIBuilder
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
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
    private lateinit var service: FailServiceImpl

    @Before
    fun setUp() {
        service = Mockito.spy(FailServiceImpl(OkHttpClient()))
    }

    @Test
    fun getFailsCompletes() {
        service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "")
                .test()
                .assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val observer = service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "")
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getFailsPostModeStreamer() {
        val observer = service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "sodapoppin")
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getFailsPostModeGame() {
        val observer = service.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "IRL", "")
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getFailsReturnsEmpty() {
        val observer = service.getFails(Int.MAX_VALUE, TimeFrame.ALL_TIME, Order.TOP, false, "", "")
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isEmpty(), "Model list is not empty")
    }

    @Test
    fun getPostModeReturnsStreamer() {
        val actual = service.getPostMode("a", "")

        assertEquals("streamer", actual)
    }

    @Test
    fun getPostModeReturnsGame() {
        val actual = service.getPostMode("", "b")

        assertEquals("game", actual)
    }

    @Test
    fun getPostModeReturnsStandard() {
        val actual = service.getPostMode("", "")

        assertEquals("standard", actual)
    }

    @Test
    fun getRequestUrlReturnsCorrectStandard() {
        val actual = service.getRequestUrl(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "")

        val expected = URIBuilder(FailServiceImpl.ENDPOINT)
                .addParameter("loadPostMode", "standard")
                .addParameter("loadPostNSFW", "0")
                .addParameter("loadPostOrder", Order.TOP.value)
                .addParameter("loadPostPage", "0")
                .addParameter("loadPostTimeFrame", TimeFrame.ALL_TIME.value)
                .build().toURL()

        assertEquals(expected, actual)
    }

    @Test
    fun getRequestUrlReturnsCorrectStandardNsfw() {
        val actual = service.getRequestUrl(0, TimeFrame.ALL_TIME, Order.TOP, true, "", "")

        val expected = URIBuilder(FailServiceImpl.ENDPOINT)
                .addParameter("loadPostMode", "standard")
                .addParameter("loadPostNSFW", "1")
                .addParameter("loadPostOrder", Order.TOP.value)
                .addParameter("loadPostPage", "0")
                .addParameter("loadPostTimeFrame", TimeFrame.ALL_TIME.value)
                .build().toURL()

        assertEquals(expected, actual)
    }

    @Test
    fun getRequestUrlReturnsCorrectGame() {
        val actual = service.getRequestUrl(0, TimeFrame.ALL_TIME, Order.TOP, false, "game", "")

        val expected = URIBuilder(FailServiceImpl.ENDPOINT)
                .addParameter("loadPostMode", "game")
                .addParameter("loadPostNSFW", "0")
                .addParameter("loadPostOrder", Order.TOP.value)
                .addParameter("loadPostPage", "0")
                .addParameter("loadPostTimeFrame", TimeFrame.ALL_TIME.value)
                .addParameter("loadPostModeGame", "game")
                .build().toURL()

        assertEquals(expected, actual)
    }

    @Test
    fun getRequestUrlReturnsCorrectStreamer() {
        val actual = service.getRequestUrl(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "streamer")

        val expected = URIBuilder(FailServiceImpl.ENDPOINT)
                .addParameter("loadPostMode", "streamer")
                .addParameter("loadPostNSFW", "0")
                .addParameter("loadPostOrder", Order.TOP.value)
                .addParameter("loadPostPage", "0")
                .addParameter("loadPostTimeFrame", TimeFrame.ALL_TIME.value)
                .addParameter("loadPostModeStreamer", "streamer")
                .build().toURL()

        assertEquals(expected, actual)
    }
}