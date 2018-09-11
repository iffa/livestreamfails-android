package digital.sogood.livestreamfails.domain.interactor

import com.nhaarman.mockito_kotlin.verify
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.domain.test.executor.TestThreadExecutor
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class CompletableUseCaseTest {
    private lateinit var useCase: CompletableUseCase<Any>

    @Before
    fun setUp() {
        useCase = Mockito.spy(TestUseCase(TestThreadExecutor(), TestPostExecutionThread()))
    }

    @Test
    fun useCaseExecutesAndDisposes() {
        useCase.execute("").subscribe(TestObserver())

        assertFalse(useCase.disposable.isDisposed)

        useCase.dispose()

        assertTrue(useCase.disposable.isDisposed)

        verify(useCase).dispose()
    }

    open inner class TestUseCase(threadExecutor: ThreadExecutor, postExecutionThread: PostExecutionThread)
        : CompletableUseCase<Any>(threadExecutor, postExecutionThread) {
        override fun buildUseCaseObservable(params: Any): Completable {
            return Completable.complete()
        }
    }

    inner class TestObserver : CompletableObserver {
        override fun onComplete() {
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onError(e: Throwable) {
        }
    }
}