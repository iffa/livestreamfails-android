package digital.sogood.livestreamfails.remote.service.game

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameServiceImplTest {
    private lateinit var service: GameService

    @Before
    fun setUp() {
        service = GameServiceImpl(OkHttpClient())
    }

    @Test
    fun getGamesCompletes() {
        service.getGames(0)
                .test()
                .assertComplete()
    }

    @Test
    fun getGamesReturnsData() {
        val observer = service.getGames(0)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isNotEmpty(), "Model list is empty")
    }

    @Test
    fun getGamesReturnsEmpty() {
        val observer = service.getGames(Int.MAX_VALUE)
                .test()
                .assertComplete()

        val models = observer.values()[0]

        assertTrue(models.isEmpty(), "Model list is not empty")
    }
}