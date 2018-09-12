package digital.sogood.livestreamfails.data.source.fail

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.data.source.FailRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.FailFactory
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

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

        val testObserver = remoteDataStore.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "").test()
        testObserver.assertComplete()
    }

    private fun stubGetFails(single: Single<List<FailEntity>>) {
        whenever(remote.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", ""))
                .thenReturn(single)
    }
}