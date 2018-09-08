package digital.sogood.livestreamfails.data.source.game

import com.nhaarman.mockito_kotlin.mock
import digital.sogood.livestreamfails.data.source.GameDataStoreFactory
import digital.sogood.livestreamfails.data.source.GameRemoteDataStore
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameDataStoreFactoryTest {
    private lateinit var dataStoreFactory: GameDataStoreFactory

    private lateinit var remoteDataStore: GameRemoteDataStore

    @Before
    fun setUp() {
        remoteDataStore = mock()

        dataStoreFactory = GameDataStoreFactory(remoteDataStore)
    }

    @Test
    fun getDataStoreTest() {
        val dataStore = dataStoreFactory.getDataStore()

        assert(dataStore is GameRemoteDataStore)
    }
}