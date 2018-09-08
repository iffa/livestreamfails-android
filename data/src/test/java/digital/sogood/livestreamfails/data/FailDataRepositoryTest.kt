package digital.sogood.livestreamfails.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.mapper.FailMapper
import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.source.FailDataStoreFactory
import digital.sogood.livestreamfails.data.source.FailRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.FailFactory
import digital.sogood.livestreamfails.domain.model.Fail
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailDataRepositoryTest {
    private lateinit var dataRepository: FailDataRepository

    private lateinit var dataStoreFactory: FailDataStoreFactory
    private lateinit var remoteDataStore: FailRemoteDataStore
    private lateinit var mapper: FailMapper

    @Before
    fun setUp() {
        dataStoreFactory = mock()
        mapper = mock()
        remoteDataStore = mock()

        dataRepository = FailDataRepository(dataStoreFactory, mapper)

        stubDataStoreFactory()
    }

    @Test
    fun getFailsCompletes() {
        stubGetFails(Single.just(
                FailFactory.makeFailEntityList(2)
        ))
        val testObserver = dataRepository.getFails(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val items = FailFactory.makeFailList(2)
        val entities = FailFactory.makeFailEntityList(2)

        items.forEachIndexed { index, item ->
            stubMapFromEntity(entities[index], item)
        }

        stubGetFails(Single.just(entities))

        val testObserver = dataRepository.getFails(0).test()
        testObserver.assertValue(items)
    }

    private fun stubDataStoreFactory() {
        whenever(dataStoreFactory.getDataStore())
                .thenReturn(remoteDataStore)
    }

    private fun stubGetFails(single: Single<List<FailEntity>>) {
        whenever(remoteDataStore.getFails(ArgumentMatchers.any()))
                .thenReturn(single)
    }

    private fun stubMapFromEntity(entity: FailEntity,
                                  item: Fail) {
        whenever(mapper.mapFromEntity(entity))
                .thenReturn(item)
    }
}