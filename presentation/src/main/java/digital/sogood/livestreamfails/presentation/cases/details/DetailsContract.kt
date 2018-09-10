package digital.sogood.livestreamfails.presentation.cases.details

import digital.sogood.livestreamfails.presentation.model.DetailsView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface DetailsContract : TiView {
    fun showProgress()

    fun hideProgress()

    fun showDetails(details: DetailsView)

    fun hideDetails()

    fun showErrorState()

    fun hideErrorState()
}