package digital.sogood.livestreamfails.presentation.cases.details

import digital.sogood.livestreamfails.presentation.model.DetailsView
import net.grandcentrix.thirtyinch.TiView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface DetailsContract : TiView {
    fun showProgress()

    fun hideProgress()

    fun showVideo(details: DetailsView)

    fun hideVideo()

    fun showErrorState()

    fun hideErrorState()
}