package digital.sogood.livestreamfails.data.source.fail

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.data.source.FailRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.FailFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailRemoteDataStoreTest {
    private lateinit var remoteDataStore: FailRemoteDataStore

    private lateinit var remote: FailRemote

    @Before
    fun setUp() {
        remote = mock()
        remoteDataStore = FailRemoteDataStore(remote)
    }

    @Test
    fun getFailsTest() {
        stubGetFails(Single.just(FailFactory.makeFailEntityList(2)))

        val testObserver = remote.getFails(0, null, null, null, null, null).test()
        testObserver.assertComplete()
    }

    private fun stubGetFails(single: Single<List<FailEntity>>) {
        whenever(remote.getFails(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
                ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(single)
    }
}