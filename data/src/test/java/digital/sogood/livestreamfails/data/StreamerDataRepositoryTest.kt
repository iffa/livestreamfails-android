package digital.sogood.livestreamfails.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.mapper.StreamerMapper
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.data.source.StreamerDataStoreFactory
import digital.sogood.livestreamfails.data.source.StreamerRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.StreamerFactory
import digital.sogood.livestreamfails.domain.model.Streamer
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerDataRepositoryTest {
    private lateinit var dataRepository: StreamerDataRepository

    private lateinit var dataStoreFactory: StreamerDataStoreFactory
    private lateinit var remoteDataStore: StreamerRemoteDataStore
    private lateinit var mapper: StreamerMapper

    @Before
    fun setUp() {
        dataStoreFactory = mock()
        mapper = mock()
        remoteDataStore = mock()

        dataRepository = StreamerDataRepository(dataStoreFactory, mapper)

        stubDataStoreFactory()
    }

    @Test
    fun getFailsCompletes() {
        stubGetFails(Single.just(
                StreamerFactory.makeStreamerEntityList(2)
        ))
        val testObserver = dataRepository.getStreamers(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val items = StreamerFactory.makeStreamerList(2)
        val entities = StreamerFactory.makeStreamerEntityList(2)

        items.forEachIndexed { index, fail ->
            stubMapFromEntity(entities[index], fail)
        }

        stubGetFails(Single.just(entities))

        val testObserver = dataRepository.getStreamers(0).test()
        testObserver.assertValue(items)
    }

    private fun stubDataStoreFactory() {
        whenever(dataStoreFactory.getDataStore())
                .thenReturn(remoteDataStore)
    }

    private fun stubGetFails(single: Single<List<StreamerEntity>>) {
        whenever(remoteDataStore.getStreamers(ArgumentMatchers.any()))
                .thenReturn(single)
    }

    private fun stubMapFromEntity(entity: StreamerEntity,
                                  item: Streamer) {
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(item)
    }
}