package digital.sogood.livestreamfails.data.source.streamer

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import digital.sogood.livestreamfails.data.source.StreamerRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.StreamerFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerRemoteDataStoreTest {
    private lateinit var remoteDataStore: StreamerRemoteDataStore

    private lateinit var remote: StreamerRemote

    @Before
    fun setUp() {
        remote = mock()
        remoteDataStore = StreamerRemoteDataStore(remote)
    }

    @Test
    fun getStreamersTest() {
        stubGetStreamers(Single.just(StreamerFactory.makeStreamerEntityList(2)))

        val testObserver = remote.getStreamers(0).test()
        testObserver.assertComplete()
    }

    private fun stubGetStreamers(single: Single<List<StreamerEntity>>) {
        whenever(remote.getStreamers(ArgumentMatchers.any()))
                .thenReturn(single)
    }

}