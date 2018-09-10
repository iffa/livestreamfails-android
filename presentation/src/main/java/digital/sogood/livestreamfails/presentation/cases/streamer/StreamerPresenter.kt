package digital.sogood.livestreamfails.presentation.cases.streamer

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.StreamerParams
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.presentation.mapper.StreamerViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class StreamerPresenter @Inject constructor(private val useCase: SingleUseCase<List<Streamer>, StreamerParams>,
                                                 private val mapper: StreamerViewMapper)
    : TiPresenter<StreamerContract>() {
    internal var currentPage = -1

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveStreamers() {
        currentPage++

        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), StreamerParams(currentPage))
    }

    internal fun handleSuccess(streamers: List<Streamer>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (streamers.isNotEmpty()) {
                hideEmptyState()
                showStreamers(streamers.map { mapper.mapToView(it) })
            } else {
                if (currentPage > 0) {
                    showNoMoreResultsState()
                } else {
                    hideStreamers()
                    showEmptyState()
                }
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Streamer>>() {
        override fun onSuccess(t: List<Streamer>) = handleSuccess(t)

        /**
         * TODO: When encountering an error, the implementing party should have the option of retrying the request, if we are on page 10 for example. We don't want to lose all previous results.
         */
        override fun onError(e: Throwable) {
            deliverToView {
                hideProgress()
                hideStreamers()
                hideEmptyState()
                showErrorState()
            }
        }
    }
}