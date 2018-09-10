package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.presentation.model.FailView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailContract : TiView {
    fun showProgress()

    fun hideProgress()

    fun showFails(fails: List<FailView>)

    fun hideFails()

    fun showErrorState()

    fun hideErrorState()

    fun showEmptyState()

    fun hideEmptyState()
}