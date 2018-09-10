package digital.sogood.livestreamfails.presentation.cases.details

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.DetailsParams
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class DetailsPresenter @Inject constructor(private val useCase: SingleUseCase<Details, DetailsParams>,
                                                private val mapper: DetailsViewMapper)
    : TiPresenter<DetailsContract>() {
    override fun onCreate() {
        super.onCreate()

        retrieveDetails()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveDetails() {
        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber())
    }

    internal fun handleGetGamesSuccess(details: Details) {
        deliverToView {
            hideProgress()
            hideErrorState()

            showDetails(mapper.mapToView(details))
        }
    }

    inner class Subscriber : DisposableSingleObserver<Details>() {
        override fun onSuccess(t: Details) = handleGetGamesSuccess(t)

        override fun onError(e: Throwable) {
            deliverToView {
                hideProgress()
                hideDetails()
                showErrorState()
            }
        }
    }

}