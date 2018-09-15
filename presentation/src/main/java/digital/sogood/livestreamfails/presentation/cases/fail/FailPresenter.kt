package digital.sogood.livestreamfails.presentation.cases.fail

import com.github.ajalt.timberkt.Timber
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
    internal var loading = false
    internal var noMoreResults = false

    public override fun onCreate() {
        super.onCreate()

        Timber.v { "#onCreate called" }

        firstLoad()
    }

    override fun onDestroy() {
        super.onDestroy()

        Timber.v { "#onDestroy called" }

        useCase.dispose()
    }

    /**
     * Retrieve all fails with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFails(timeFrame: TimeFrame, order: Order, nsfw: Boolean) {
        Timber.d { "#retrieveFails(timeFrame, order, nsfw) called" }
        retrieveFails(timeFrame, order, "", "", nsfw)
    }

    /**
     * Retrieve all fails for [streamer] with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFailsForStreamer(streamer: String, timeFrame: TimeFrame, order: Order, nsfw: Boolean) {
        Timber.d { "#retrieveFailsForStreamer called" }
        retrieveFails(timeFrame, order, streamer, "", nsfw)
    }

    /**
     * Retrieve all fails for [game] with the specified [TimeFrame] and [Order] parameters.
     */
    fun retrieveFailsForGame(game: String, timeFrame: TimeFrame, order: Order, nsfw: Boolean) {
        Timber.d { "#retrieveFailsForGame called" }
        retrieveFails(timeFrame, order, "", game, nsfw)
    }

    /**
     * TODO: Get default parameters from outside source
     */
    internal open fun firstLoad() {
        Timber.v { "#firstLoad called" }
        retrieveFails(TimeFrame.DAY, Order.HOT, false)
    }

    private fun retrieveFails(timeFrame: TimeFrame, order: Order, streamer: String, game: String, nsfw: Boolean) {
        // Already loading, do nothing
        if (loading) return

        currentPage++

        handleChangedParams(FailParams(currentPage, timeFrame, order, nsfw, game, streamer))

        // No more results (even after param check), do nothing
        if (noMoreResults) return

        deliverToView {
            showProgress()
        }

        // This should never be null, because handleChangedParams gives it a value
        loading = true

        Timber.d { "Starting retrieval of fails, params: $currentParams, page: $currentPage, loading: $loading" }
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
                Timber.d { "Query params have changed, resetting state" }

                noMoreResults = false
                currentPage = 0
                deliverToView {
                    clearFails()
                }
            }
        }
        currentParams = newParams
    }

    internal fun handleSuccess(fails: List<Fail>) {
        loading = false

        deliverToView {
            hideProgress()
            hideErrorState()

            if (fails.isNotEmpty()) {
                hideEmptyState()
                showFails(fails.map { mapper.mapToView(it) })
            } else {
                if (currentPage > 0) {
                    noMoreResults = true
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
            Timber.e(e) { "Failed to retrieve fails" }

            loading = false

            deliverToView {
                hideProgress()
                hideFails()
                hideEmptyState()
                showErrorState()
            }
        }
    }
}