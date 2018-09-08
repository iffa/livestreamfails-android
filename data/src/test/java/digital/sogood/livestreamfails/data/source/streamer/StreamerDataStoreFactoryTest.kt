package digital.sogood.livestreamfails.data.source.streamer

import com.nhaarman.mockito_kotlin.mock
import digital.sogood.livestreamfails.data.source.StreamerDataStoreFactory
import digital.sogood.livestreamfails.data.source.StreamerRemoteDataStore
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerDataStoreFactoryTest {
    private lateinit var dataStoreFactory: StreamerDataStoreFactory

    private lateinit var remoteDataStore: StreamerRemoteDataStore

    @Before
    fun setUp() {
        remoteDataStore = mock()

        dataStoreFactory = StreamerDataStoreFactory(remoteDataStore)
    }

    @Test
    fun getDataStoreTest() {
        val dataStore = dataStoreFactory.getDataStore()

        assert(dataStore is StreamerRemoteDataStore)
    }
}