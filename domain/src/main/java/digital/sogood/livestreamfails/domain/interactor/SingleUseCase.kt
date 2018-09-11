package digital.sogood.livestreamfails.domain.interactor

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

/**
 * Abstract class for a UseCase that returns an instance of a [Single].
 */
abstract class SingleUseCase<T, in Params> constructor(
        private val threadExecutor: ThreadExecutor,
        private val postExecutionThread: PostExecutionThread) {

    internal val disposables = CompositeDisposable()

    /**
     * Builds a [Single] which will be used when the current [SingleUseCase] is executed.
     */
    protected abstract fun buildUseCaseObservable(params: Params): Single<T>

    /**
     * Executes the current use case.
     */
    open fun execute(singleObserver: DisposableSingleObserver<T>, params: Params) {
        val single = this.buildUseCaseObservable(params)
                .subscribeOn(Schedulers.from(threadExecutor))
                .observeOn(postExecutionThread.scheduler)
        addDisposable(single.subscribeWith(singleObserver))
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    open fun dispose() {
        disposables.dispose()
    }

    /**
     * Dispose from current [CompositeDisposable].
     */
    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }
}