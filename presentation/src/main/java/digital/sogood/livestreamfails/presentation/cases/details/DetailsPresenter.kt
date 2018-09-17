package digital.sogood.livestreamfails.presentation.cases.details

import digital.sogood.livestreamfails.domain.interactor.cases.DetailsParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetDetails
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * TODO: Injection scope, as Singleton would not work in this case
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class DetailsPresenter @Inject constructor(private val useCase: GetDetails,
                                                private val mapper: DetailsViewMapper)
    : TiPresenter<DetailsContract>() {
    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveDetails(postId: Long) {
        retrieveDetails(DetailsParams(postId))
    }

    internal fun retrieveDetails(params: DetailsParams) {
        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), params)
    }

    internal fun handleSuccess(details: Details) {
        deliverToView {
            hideProgress()
            hideErrorState()

            showVideo(mapper.mapToView(details))
        }
    }

    inner class Subscriber : DisposableSingleObserver<Details>() {
        override fun onSuccess(t: Details) = handleSuccess(t)

        override fun onError(e: Throwable) {
            deliverToView {
                hideProgress()
                hideVideo()
                showErrorState()
            }
        }
    }

}