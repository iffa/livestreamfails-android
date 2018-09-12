package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetFails
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailPresenter @Inject constructor(private val useCase: GetFails,
                                             private val mapper: FailViewMapper)
    : TiPresenter<FailContract>() {
    private var currentParams: FailParams? = null
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
    fun retrieveFailsForStreamer(streamer: String, timeFrame: TimeFrame, order: Order) {
        retrieveFails(timeFrame, order, streamer, "")
    }

    /**
     * Retrieve all fails for [game] with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFailsForGame(game: String, timeFrame: TimeFrame, order: Order) {
        retrieveFails(timeFrame, order, "", game)
    }

    private fun retrieveFails(timeFrame: TimeFrame, order: Order, streamer: String, game: String) {
        currentPage++

        handleChangedParams(FailParams(currentPage, timeFrame, order, false, game, streamer))

        deliverToView {
            showProgress()
        }

        // This should never be null, because handleChangedParams gives it a value
        currentParams?.let {
            useCase.execute(Subscriber(), FailParams(currentPage, it.timeFrame,
                    it.order, it.nsfw, it.game, it.streamer))
        }
    }

    /**
     * If parameters have changed (other than page), reset state.
     */
    private fun handleChangedParams(newParams: FailParams) {
        currentParams?.let {
            if (!it.equalsIgnorePage(newParams)) {
                currentPage = 0
                deliverToView {
                    clearFails()
                }
            }
        }
        currentParams = newParams
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