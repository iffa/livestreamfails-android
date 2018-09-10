package digital.sogood.livestreamfails.presentation.cases.game

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.GameParams
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.presentation.mapper.GameViewMapper
import net.grandcentrix.thirtyinch.TiPresenter
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class GamePresenter @Inject constructor(private val useCase: SingleUseCase<List<Game>, GameParams>,
                                             private val mapper: GameViewMapper)
    : TiPresenter<GameContract>() {
}