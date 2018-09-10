package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.presentation.model.FailView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailContract : TiView {
    fun showProgress()

    fun hideProgress()

    /**
     * Called when the presenter has new results. Note that [fails] includes only the new
     * result set.
     */
    fun showFails(fails: List<FailView>)

    /**
     * Called when previous results should be cleared. This mainly occurs when query parameters
     * are not the same between results, i.e. filters have changed and the whole result set needs
     * to be updated.
     */
    fun clearFails()

    fun hideFails()

    fun showErrorState()

    fun hideErrorState()

    fun showEmptyState()

    fun hideEmptyState()

    /**
     * Called when a page is empty, indicating the end of results.
     * If the first page is empty, [showEmptyState] is called instead.
     */
    fun showNoMoreResultsState()
}