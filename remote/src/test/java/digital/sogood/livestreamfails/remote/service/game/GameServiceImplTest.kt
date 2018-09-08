package digital.sogood.livestreamfails.remote.service.game

import okhttp3.OkHttpClient
import org.junit.Before
import org.junit.Test

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

        kotlin.test.assertTrue(models.isNotEmpty(), "Model list is empty")
    }
}