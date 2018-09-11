package digital.sogood.livestreamfails.domain.interactor.cases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import digital.sogood.livestreamfails.domain.test.factory.StreamerFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GetStreamersTest {
    private lateinit var useCase: GetStreamers

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockRepository: StreamerRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockRepository = mock()

        useCase = GetStreamers(mockRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val fails = StreamerFactory.makeStreamerList(2)
        stubGetStreamers(Single.just(fails))
        val testObserver = useCase.buildUseCaseObservable(StreamerParams(0)).test()
        testObserver.assertValue(fails)
    }

    private fun stubGetStreamers(single: Single<List<Streamer>>) {
        whenever(mockRepository.getStreamers(ArgumentMatchers.any()))
                .thenReturn(single)
    }

}