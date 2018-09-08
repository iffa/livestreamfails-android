package digital.sogood.livestreamfails.remote.service.fail

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Test for the implementation of [FailService]. Note that this test requires internet access,
 * as functionality is verified by actually calling the API and trying to parse it successfully.
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
        service.getFails(0)
                .test()
                .assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val observer = service.getFails(0)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }
}