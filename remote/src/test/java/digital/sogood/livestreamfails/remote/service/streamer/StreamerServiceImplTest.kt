package digital.sogood.livestreamfails.remote.service.streamer

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

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

        kotlin.test.assertTrue(models.isNotEmpty(), "Model list is empty")
    }
}