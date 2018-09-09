package digital.sogood.livestreamfails.remote.service.streamer

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerServiceImplTest {
    private lateinit var service: StreamerService

    @Before
    fun setUp() {
        service = StreamerServiceImpl(OkHttpClient())
    }

    @Test
    fun getStreamersCompletes() {
        service.getStreamers(0)
                .test()
                .assertComplete()
    }

    @Test
    fun getStreamersReturnsData() {
        val observer = service.getStreamers(0)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getStreamersReturnsEmpty() {
        val observer = service.getStreamers(Int.MAX_VALUE)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isEmpty(), "Model list is not empty")
    }
}