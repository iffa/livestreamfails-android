package digital.sogood.livestreamfails.data.source.fail

import com.nhaarman.mockito_kotlin.mock
import digital.sogood.livestreamfails.data.source.FailDataStoreFactory
import digital.sogood.livestreamfails.data.source.FailRemoteDataStore
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailDataStoreFactoryTest {
    private lateinit var dataStoreFactory: FailDataStoreFactory

    private lateinit var remoteDataStore: FailRemoteDataStore

    @Before
    fun setUp() {
        remoteDataStore = mock()

        dataStoreFactory = FailDataStoreFactory(remoteDataStore)
    }

    @Test
    fun getDataStoreTest() {
        val dataStore = dataStoreFactory.getDataStore()

        assert(dataStore is FailRemoteDataStore)
    }
}