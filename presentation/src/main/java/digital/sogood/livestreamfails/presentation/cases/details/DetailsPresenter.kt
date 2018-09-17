package digital.sogood.livestreamfails.presentation.cases.details

import com.github.ajalt.timberkt.Timber
import digital.sogood.livestreamfails.domain.interactor.cases.DetailsParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetDetails
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class DetailsPresenter @Inject constructor(private val useCase: GetDetails,
                                                private val mapper: DetailsViewMapper)
    : TiPresenter<DetailsContract>() {
    var postId: Long = -1

    override fun onCreate() {
        super.onCreate()

        firstLoad()
    }

    internal open fun firstLoad() {
        retrieveDetails()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    internal fun retrieveDetails() {
        Timber.d { "Retrieving details for id $postId" }

        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), DetailsParams(postId))
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