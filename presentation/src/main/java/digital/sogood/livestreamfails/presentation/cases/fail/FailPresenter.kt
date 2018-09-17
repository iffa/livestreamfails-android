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

    var timeFrame = TimeFrame.DAY
        private set
    var order = Order.HOT
        private set
    var nsfw = false
        private set

    public override fun onCreate() {
        super.onCreate()

        firstLoad()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    override fun onAttachView(view: FailContract) {
        super.onAttachView(view)

        Timber.d { "Attached: $timeFrame, $order, $nsfw" }
    }

    override fun onDetachView() {
        super.onDetachView()

        Timber.d { "Detached: $timeFrame, $order, $nsfw" }
    }

    fun onTimeFrameChanged(newTimeFrame: TimeFrame) {
        if (timeFrame != newTimeFrame) {
            this.timeFrame = newTimeFrame
            retrieveFails()
        }
    }

    fun onOrderChanged(newOrder: Order) {
        if (order != newOrder) {
            order = newOrder
            retrieveFails()
        }
    }

    fun onNsfwChanged(newNsfw: Boolean) {
        if (nsfw != newNsfw) {
            nsfw = newNsfw
            retrieveFails()
        }
    }

    fun onScrollToEnd() {
        retrieveFails()
    }

    /**
     * TODO: Get default parameters from outside source
     */
    internal open fun firstLoad() {
        retrieveFails()
    }

    internal fun retrieveFails() {
        val paramsChanged = handleChangedParams(FailParams(currentPage, timeFrame, order, nsfw))

        // Already loading, and params haven't changed, do nothing
        // If params have changed, continue anyway
        if (loading && !paramsChanged) {
            return
        }

        // No more results (even after param check), do nothing
        if (noMoreResults) return

        loading = true

        deliverToView {
            showProgress()
        }

        currentPage++

        Timber.d { "Starting retrieval of fails, params: $currentParams, page: $currentPage, loading: $loading" }
        currentParams?.let {
            useCase.execute(Subscriber(), FailParams(currentPage, it.timeFrame,
                    it.order, it.nsfw, it.game, it.streamer))
        }
    }

    /**
     * If parameters have changed (other than page), reset state.
     *
     * @return True if params have changed
     */
    private fun handleChangedParams(newParams: FailParams): Boolean {
        var changed = false
        currentParams?.let {
            if (!it.equalsIgnorePage(newParams)) {
                Timber.d { "Query params have changed, resetting state" }

                noMoreResults = false
                currentPage = -1
                deliverToView {
                    clearFails()
                }
                changed = true
            }
        }
        currentParams = newParams
        return changed
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