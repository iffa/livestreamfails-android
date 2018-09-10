package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * TODO: Pagination support
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailPresenter @Inject constructor(private val useCase: SingleUseCase<List<Fail>, FailParams>,
                                             private val mapper: FailViewMapper)
    : TiPresenter<FailContract>() {
    override fun onCreate() {
        super.onCreate()

        retrieveFails()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveFails() {
        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber())
    }

    internal fun handleGetFailsSuccess(fails: List<Fail>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (fails.isNotEmpty()) {
                hideEmptyState()
                showFails(fails.map { mapper.mapToView(it) })
            } else {
                hideFails()
                showEmptyState()
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Fail>>() {
        override fun onSuccess(t: List<Fail>) = handleGetFailsSuccess(t)

        override fun onError(e: Throwable) {
            deliverToView {
                hideProgress()
                hideFails()
                hideEmptyState()
                showErrorState()
            }
        }
    }
}