package digital.sogood.livestreamfails.remote.service.game

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.remote.mapper.GameEntityMapper
import digital.sogood.livestreamfails.remote.model.GameModel
import digital.sogood.livestreamfails.remote.test.factory.GameFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameRemoteImplTest {
    private lateinit var entityMapper: GameEntityMapper
    private lateinit var gameService: GameService

    private lateinit var remoteImpl: GameRemoteImpl

    @Before
    fun setUp() {
        entityMapper = mock()
        gameService = mock()

        remoteImpl = GameRemoteImpl(gameService, entityMapper)
    }

    @Test
    fun getGamesCompletes() {
        stubGetGames(Single.just(
                GameFactory.makeGameModelList(2)
        ))

        val testObserver = remoteImpl.getGames(0).test()

        testObserver.assertComplete()
    }

    @Test
    fun getGamesReturnsData() {
        val models = GameFactory.makeGameModelList(2)
        stubGetGames(Single.just(models))

        val entities = mutableListOf<GameEntity>()
        models.forEach {
            entities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = remoteImpl.getGames(0).test()

        testObserver.assertValue(entities)
    }

    private fun stubGetGames(single: Single<List<GameModel>>) {
        whenever(gameService.getGames(ArgumentMatchers.anyInt()))
                .thenReturn(single)
    }
}