package digital.sogood.livestreamfails.data.source.game

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.repository.game.GameRemote
import digital.sogood.livestreamfails.data.source.GameRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.GameFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameRemoteDataStoreTest {
    private lateinit var remoteDataStore: GameRemoteDataStore

    private lateinit var remote: GameRemote

    @Before
    fun setUp() {
        remote = mock()
        remoteDataStore = GameRemoteDataStore(remote)
    }

    @Test
    fun getGamesTest() {
        stubGetGames(Single.just(GameFactory.makeGameEntityList(2)))

        val testObserver = remoteDataStore.getGames(0).test()
        testObserver.assertComplete()
    }

    private fun stubGetGames(single: Single<List<GameEntity>>) {
        whenever(remote.getGames(ArgumentMatchers.any()))
                .thenReturn(single)
    }

}