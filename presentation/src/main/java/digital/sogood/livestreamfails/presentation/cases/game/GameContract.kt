package digital.sogood.livestreamfails.presentation.cases.game

import digital.sogood.livestreamfails.presentation.model.GameView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface GameContract : TiView {
    fun showProgress()

    fun hideProgress()

    fun showGames(games: List<GameView>)

    fun hideGames()

    fun showErrorState()

    fun hideErrorState()

    fun showEmptyState()

    fun hideEmptyState()
}