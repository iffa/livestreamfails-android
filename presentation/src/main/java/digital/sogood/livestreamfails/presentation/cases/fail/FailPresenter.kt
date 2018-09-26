package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetFails
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.presentation.SettingsService
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import digital.sogood.livestreamfails.presentation.util.EspressoIdlingResource
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * TODO: Error logging without having Timber as a dependency for this module
 * TODO: When encountering an error, the implementing party should have the option of retrying the request, if we are on page 10 for example. We don't want to lose all previous results.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailPresenter @Inject constructor(private val useCase: GetFails,
                                             private val mapper: FailViewMapper,
                                             settings: SettingsService)
    : TiPresenter<FailContract>() {
    private var currentParams: FailParams? = null
    internal var currentPage = -1
    internal var loading = false
    internal var noMoreResults = false

    var timeFrame = settings.getDefaultTimeFrame()
        private set
    var order = settings.getDefaultOrder()
        private set
    var nsfw = settings.shouldShowNsfwContent()
        private set

    private val nsfwSubscriber: Disposable = settings.shouldShowNsfwContentObservable().subscribe {
        onNsfwChanged(it)
    }

    public override fun onCreate() {
        super.onCreate()

        firstLoad()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
        nsfwSubscriber.dispose()
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

    internal fun onNsfwChanged(newNsfw: Boolean) {
        if (nsfw != newNsfw) {
            nsfw = newNsfw
            retrieveFails()
        }
    }

    fun onScrollToEnd() {
        retrieveFails()
    }

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

        currentPage++

        deliverToView {
            if (currentPage == 0) {
                showProgress()
            } else {
                showLoadingMoreProgress()
            }
        }

        EspressoIdlingResource.increment()
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
                // Clear use case, in case it is already loading something that is now outdated
                useCase.clear()

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
        if (!EspressoIdlingResource.idlingResource.isIdleNow) {
            EspressoIdlingResource.decrement()
        }

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

        override fun onError(e: Throwable) {
            if (!EspressoIdlingResource.idlingResource.isIdleNow) {
                EspressoIdlingResource.decrement()
            }

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