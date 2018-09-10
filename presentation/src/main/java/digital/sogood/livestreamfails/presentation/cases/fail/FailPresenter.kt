package digital.sogood.livestreamfails.presentation.cases.fail

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import net.grandcentrix.thirtyinch.TiPresenter
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailPresenter @Inject constructor(private val useCase: SingleUseCase<List<Fail>, FailParams>,
                                             private val mapper: FailViewMapper)
    : TiPresenter<FailContract>() {
}