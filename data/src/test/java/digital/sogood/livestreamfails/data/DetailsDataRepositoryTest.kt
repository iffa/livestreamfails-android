package digital.sogood.livestreamfails.data

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.mapper.DetailsMapper
import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.data.source.DetailsDataStoreFactory
import digital.sogood.livestreamfails.data.source.DetailsRemoteDataStore
import digital.sogood.livestreamfails.data.test.factory.DetailsFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsDataRepositoryTest {
    private val mapper = DetailsMapper()

    private lateinit var dataRepository: DetailsDataRepository

    private lateinit var dataStoreFactory: DetailsDataStoreFactory
    private lateinit var remoteDataStore: DetailsRemoteDataStore

    @Before
    fun setUp() {
        dataStoreFactory = mock()
        remoteDataStore = mock()

        dataRepository = DetailsDataRepository(dataStoreFactory, mapper)

        stubDataStoreFactory()
    }

    @Test
    fun getDetailsCompletes() {
        stubGetDetails(Single.just(
                DetailsFactory.makeDetailsEntity()
        ))
        val testObserver = dataRepository.getDetails(0).test()
        testObserver.assertComplete()
    }

    @Test
    fun getDetailsReturnsData() {
        val item = DetailsFactory.makeDetails()
        val entity = mapper.mapToEntity(item)

        stubGetDetails(Single.just(entity))

        val testObserver = dataRepository.getDetails(0).test()
        testObserver.assertValue(item)
    }

    private fun stubDataStoreFactory() {
        whenever(dataStoreFactory.getDataStore())
                .thenReturn(remoteDataStore)
    }

    private fun stubGetDetails(single: Single<DetailsEntity>) {
        whenever(remoteDataStore.getDetails(ArgumentMatchers.anyLong()))
                .thenReturn(single)
    }
}