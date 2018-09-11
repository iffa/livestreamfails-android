package digital.sogood.livestreamfails.domain.interactor.cases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.domain.test.factory.FailFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GetFailsTest {
    private lateinit var useCase: GetFails

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockRepository: FailRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockRepository = mock()

        useCase = GetFails(mockRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val fails = FailFactory.makeFailList(2)
        stubGetFails(Single.just(fails))
        val testObserver = useCase.buildUseCaseObservable(FailParams(0, TimeFrame.ALL_TIME, Order.TOP, false, "", "")).test()
        testObserver.assertValue(fails)
    }

    private fun stubGetFails(single: Single<List<Fail>>) {
        whenever(mockRepository.getFails(0, TimeFrame.ALL_TIME, Order.TOP, false, "", ""))
                .thenReturn(single)
    }
}