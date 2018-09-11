package digital.sogood.livestreamfails.domain.interactor.cases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.domain.test.factory.DetailsFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GetDetailsTest {
    private lateinit var useCase: GetDetails

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockRepository: DetailsRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockRepository = mock()

        useCase = GetDetails(mockRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val details = DetailsFactory.makeDetails()
        stubGetDetails(Single.just(details))
        val testObserver = useCase.buildUseCaseObservable(DetailsParams(0)).test()
        testObserver.assertValue(details)
    }

    private fun stubGetDetails(single: Single<Details>) {
        whenever(mockRepository.getDetails(0))
                .thenReturn(single)
    }

}