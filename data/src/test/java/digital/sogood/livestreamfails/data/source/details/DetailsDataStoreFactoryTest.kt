package digital.sogood.livestreamfails.data.source.details

import com.nhaarman.mockito_kotlin.mock
import digital.sogood.livestreamfails.data.source.DetailsDataStoreFactory
import digital.sogood.livestreamfails.data.source.DetailsRemoteDataStore
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsDataStoreFactoryTest {
    private lateinit var dataStoreFactory: DetailsDataStoreFactory

    private lateinit var remoteDataStore: DetailsRemoteDataStore

    @Before
    fun setUp() {
        remoteDataStore = mock()

        dataStoreFactory = DetailsDataStoreFactory(remoteDataStore)
    }

    @Test
    fun getDataStoreTest() {
        val dataStore = dataStoreFactory.getDataStore()

        assert(dataStore is DetailsRemoteDataStore)
    }
}