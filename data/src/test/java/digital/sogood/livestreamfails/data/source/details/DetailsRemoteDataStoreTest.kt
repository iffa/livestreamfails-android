package digital.sogood.livestreamfails.data.source.details

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.data.repository.details.DetailsRemote
import digital.sogood.livestreamfails.data.source.DetailsRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.DetailsFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsRemoteDataStoreTest {
    private lateinit var remoteDataStore: DetailsRemoteDataStore

    private lateinit var remote: DetailsRemote

    @Before
    fun setUp() {
        remote = mock()
        remoteDataStore = DetailsRemoteDataStore(remote)
    }

    @Test
    fun getDetailsTest() {
        stubGetFails(Single.just(DetailsFactory.makeDetailsEntity()))

        val testObserver = remote.getDetails(0).test()
        testObserver.assertComplete()
    }

    private fun stubGetFails(single: Single<DetailsEntity>) {
        whenever(remote.getDetails(ArgumentMatchers.anyLong()))
                .thenReturn(single)
    }

}