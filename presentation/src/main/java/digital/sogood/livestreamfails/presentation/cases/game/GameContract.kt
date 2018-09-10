package digital.sogood.livestreamfails.presentation.cases.game

import digital.sogood.livestreamfails.presentation.model.GameView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface GameContract : TiView {
    fun showProgress()

    fun hideProgress()

    /**
     * Called when the presenter has new results. Note that [games] includes only the new
     * result set.
     */
    fun showGames(games: List<GameView>)

    fun hideGames()

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