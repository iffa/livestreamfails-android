package digital.sogood.livestreamfails.remote.service.streamer

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.remote.mapper.StreamerEntityMapper
import digital.sogood.livestreamfails.remote.model.StreamerModel
import digital.sogood.livestreamfails.remote.test.factory.StreamerFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerRemoteImplTest {
    private lateinit var entityMapper: StreamerEntityMapper
    private lateinit var streamerService: StreamerService

    private lateinit var remoteImpl: StreamerRemoteImpl

    @Before
    fun setUp() {
        entityMapper = mock()
        streamerService = mock()

        remoteImpl = StreamerRemoteImpl(streamerService, entityMapper)
    }

    @Test
    fun getStreamersCompletes() {
        stubGetStreamers(Single.just(
                StreamerFactory.makeStreamerModelList(2)
        ))

        val testObserver = remoteImpl.getStreamers(0).test()

        testObserver.assertComplete()
    }

    @Test
    fun getStreamersReturnsData() {
        val models = StreamerFactory.makeStreamerModelList(2)
        stubGetStreamers(Single.just(models))

        val entities = mutableListOf<StreamerEntity>()
        models.forEach {
            entities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = remoteImpl.getStreamers(0).test()

        testObserver.assertValue(entities)
    }

    private fun stubGetStreamers(single: Single<List<StreamerModel>>) {
        whenever(streamerService.getStreamers(ArgumentMatchers.anyInt()))
                .thenReturn(single)
    }
}