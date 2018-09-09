package digital.sogood.livestreamfails.remote.service.details

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotNull

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsServiceImplTest {
    private lateinit var service: DetailsService

    companion object {
        private const val VALID_ID: Long = 18645
        private const val INVALID_ID: Long = 0
    }

    @Before
    fun setUp() {
        service = DetailsServiceImpl(OkHttpClient())
    }

    @Test
    fun getFailsCompletes() {
        service.getDetails(VALID_ID)
                .test()
                .assertComplete()
    }

    @Test
    fun getDetailsErrorForInvalidId() {
        service.getDetails(INVALID_ID)
                .test()
                .assertError(DetailsServiceImpl.InvalidPostIdException::class.java)
    }

    @Test
    fun getFailsReturnsData() {
        val observer = service.getDetails(VALID_ID)
                .test()
                .assertComplete()

        val model = observer.values()[0]

        assertNotNull(model, "Model cannot be null")
    }
}