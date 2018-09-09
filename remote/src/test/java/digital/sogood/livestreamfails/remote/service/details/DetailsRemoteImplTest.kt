package digital.sogood.livestreamfails.remote.service.details

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.remote.mapper.DetailsEntityMapper
import digital.sogood.livestreamfails.remote.model.DetailsModel
import digital.sogood.livestreamfails.remote.test.factory.DetailsFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsRemoteImplTest {
    private val entityMapper = DetailsEntityMapper()

    private lateinit var service: DetailsService
    private lateinit var remoteImpl: DetailsRemoteImpl

    @Before
    fun setUp() {
        service = mock()

        remoteImpl = DetailsRemoteImpl(service, entityMapper)
    }

    @Test
    fun getDetailsCompletes() {
        stubGetDetails(Single.just(
                DetailsFactory.makeDetailsModel()
        ))

        val testObserver = remoteImpl.getDetails(0).test()

        testObserver.assertComplete()
    }

    @Test
    fun getDetailsReturnsData() {
        val model = DetailsFactory.makeDetailsModel()
        stubGetDetails(Single.just(model))

        val entity = entityMapper.mapFromRemote(model)

        val testObserver = remoteImpl.getDetails(0).test()

        testObserver.assertValue(entity)
    }

    private fun stubGetDetails(single: Single<DetailsModel>) {
        whenever(service.getDetails(ArgumentMatchers.anyLong()))
                .thenReturn(single)
    }
}