package digital.sogood.livestreamfails.domain.interactor

import com.nhaarman.mockito_kotlin.mock
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.domain.test.executor.TestThreadExecutor
import io.reactivex.Single
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me></me>@santeri.xyz>
 */
class SingleUseCaseTest {
    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread

    private lateinit var useCase: SingleUseCase<Any, Any>

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()

        useCase = Mockito.spy(TestUseCase(TestThreadExecutor(), TestPostExecutionThread()))
    }

    @Test
    fun useCaseExecutesAndDisposes() {
        useCase.execute(TestSubscriber(), "")

        assertEquals(useCase.disposables.size(), 1)

        useCase.dispose()

        assertTrue(useCase.disposables.isDisposed)
    }

    @Test
    fun useCaseExecutesAndClears() {
        useCase.execute(TestSubscriber(), "")

        assertEquals(useCase.disposables.size(), 1)

        useCase.clear()

        assertEquals(useCase.disposables.size(), 0)
    }

    open inner class TestUseCase(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread)
        : SingleUseCase<Any, Any>(threadExecutor, postExecutionThread) {
        override fun buildUseCaseObservable(params: Any): Single<Any> {
            return Single.just(true)
        }
    }

    inner class TestSubscriber : DisposableSingleObserver<Any>() {
        override fun onSuccess(t: Any) {
        }

        override fun onError(e: Throwable) {
        }
    }
}