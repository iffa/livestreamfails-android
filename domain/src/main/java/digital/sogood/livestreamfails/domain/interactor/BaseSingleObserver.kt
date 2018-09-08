package digital.sogood.livestreamfails.domain.interactor

import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable

open class BaseSingleObserver<T> : SingleObserver<T> {
    override fun onSubscribe(d: Disposable) {}

    override fun onSuccess(t: T) {}

    override fun onError(exception: Throwable) {}
}