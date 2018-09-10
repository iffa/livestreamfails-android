package digital.sogood.livestreamfails.presentation.cases.details

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.DetailsParams
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import net.grandcentrix.thirtyinch.TiPresenter
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class DetailsPresenter @Inject constructor(private val useCase: SingleUseCase<Details, DetailsParams>,
                                                private val mapper: DetailsViewMapper)
    : TiPresenter<DetailsContract>() {
}