package digital.sogood.livestreamfails.remote.service.fail

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.remote.mapper.FailEntityMapper
import digital.sogood.livestreamfails.remote.model.FailModel
import digital.sogood.livestreamfails.remote.test.factory.FailFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailRemoteImplTest {
    private lateinit var entityMapper: FailEntityMapper
    private lateinit var failService: FailService

    private lateinit var remoteImpl: FailRemoteImpl

    @Before
    fun setUp() {
        entityMapper = mock()
        failService = mock()

        remoteImpl = FailRemoteImpl(failService, entityMapper)
    }

    @Test
    fun getFailsCompletes() {
        stubGetFails(Single.just(
                FailFactory.makeFailModelList(2)
        ))

        val testObserver = remoteImpl.getFails(0).test()

        testObserver.assertComplete()
    }

    @Test
    fun getFailsReturnsData() {
        val models = FailFactory.makeFailModelList(2)
        stubGetFails(Single.just(models))

        val entities = mutableListOf<FailEntity>()
        models.forEach {
            entities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = remoteImpl.getFails(0).test()

        testObserver.assertValue(entities)
    }

    private fun stubGetFails(single: Single<List<FailModel>>) {
        whenever(failService.getFails(ArgumentMatchers.anyInt()))
                .thenReturn(single)
    }
}