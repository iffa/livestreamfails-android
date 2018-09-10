package digital.sogood.livestreamfails.presentation.cases.streamer

import digital.sogood.livestreamfails.presentation.model.StreamerView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface StreamerContract : TiView {
    fun showProgress()

    fun hideProgress()

    fun showStreamers(streamers: List<StreamerView>)

    fun hideStreamers()

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