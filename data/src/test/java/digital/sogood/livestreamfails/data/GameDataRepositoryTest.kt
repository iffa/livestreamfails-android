package digital.sogood.livestreamfails.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.mapper.GameMapper
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.source.GameDataStoreFactory
import digital.sogood.livestreamfails.data.source.GameRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.GameFactory
import digital.sogood.livestreamfails.domain.model.Game
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameDataRepositoryTest {
    private lateinit var dataRepository: GameDataRepository

    private lateinit var dataStoreFactory: GameDataStoreFactory
    private lateinit var remoteDataStore: GameRemoteDataStore
    private lateinit var mapper: GameMapper

    @Before
    fun setUp() {
        dataStoreFactory = mock()
        mapper = mock()
        remoteDataStore = mock()

        dataRepository = GameDataRepository(dataStoreFactory, mapper)

        stubDataStoreFactory()
    }

    @Test
    fun getGamesCompletes() {
        stubGetGames(Single.just(
                GameFactory.makeGameEntityList(2)
        ))
        val testObserver = dataRepository.getGames(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getGamesReturnsData() {
        val items = GameFactory.makeGameList(2)
        val entities = GameFactory.makeGameEntityList(2)

        items.forEachIndexed { index, item ->
            stubMapFromEntity(entities[index], item)
        }

        stubGetGames(Single.just(entities))

        val testObserver = dataRepository.getGames(0).test()
        testObserver.assertValue(items)
    }

    private fun stubDataStoreFactory() {
        whenever(dataStoreFactory.getDataStore())
                .thenReturn(remoteDataStore)
    }

    private fun stubGetGames(single: Single<List<GameEntity>>) {
        whenever(remoteDataStore.getGames(ArgumentMatchers.any()))
                .thenReturn(single)
    }

    private fun stubMapFromEntity(entity: GameEntity,
                                  item: Game) {
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(item)
    }
}