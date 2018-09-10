package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * TODO: Proper handling when [FailParams] are changed.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailPresenter @Inject constructor(private val useCase: SingleUseCase<List<Fail>, FailParams>,
                                             private val mapper: FailViewMapper)
    : TiPresenter<FailContract>() {
    internal var currentPage = -1

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    /**
     * Retrieve all fails with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFails(timeFrame: TimeFrame, order: Order) {
        retrieveFails(timeFrame, order, "", "")
    }

    /**
     * Retrieve all fails for [streamer] with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFailsForStreamer(streamer: String, timeFrame: TimeFrame = TimeFrame.DAY, order: Order = Order.HOT) {
        retrieveFails(timeFrame, order, streamer, "")
    }

    /**
     * Retrieve all fails for [game] with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFailsForGame(game: String, timeFrame: TimeFrame = TimeFrame.DAY, order: Order = Order.HOT) {
        retrieveFails(timeFrame, order, "", game)
    }

    private fun retrieveFails(timeFrame: TimeFrame, order: Order, streamer: String, game: String) {
        currentPage++

        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), FailParams(currentPage, timeFrame, order, streamer = streamer, game = game))
    }

    internal fun handleSuccess(fails: List<Fail>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (fails.isNotEmpty()) {
                hideEmptyState()
                showFails(fails.map { mapper.mapToView(it) })
            } else {
                if (currentPage > 0) {
                    showNoMoreResultsState()
                } else {
                    hideFails()
                    showEmptyState()
                }
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Fail>>() {
        override fun onSuccess(t: List<Fail>) = handleSuccess(t)

        /**
         * TODO: When encountering an error, the implementing party should have the option of retrying the request, if we are on page 10 for example. We don't want to lose all previous results.
         */
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