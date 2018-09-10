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
 * TODO: Pagination support
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class StreamerPresenter @Inject constructor(private val useCase: SingleUseCase<List<Streamer>, StreamerParams>,
                                                 private val mapper: StreamerViewMapper)
    : TiPresenter<StreamerContract>() {
    override fun onCreate() {
        super.onCreate()

        retrieveStreamers(StreamerParams())
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveStreamers(params: StreamerParams) {
        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), params)
    }

    internal fun handleGetStreamersSuccess(streamers: List<Streamer>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (streamers.isNotEmpty()) {
                hideEmptyState()
                showStreamers(streamers.map { mapper.mapToView(it) })
            } else {
                hideStreamers()
                showEmptyState()
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Streamer>>() {
        override fun onSuccess(t: List<Streamer>) = handleGetStreamersSuccess(t)

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