package digital.sogood.livestreamfails.presentation.cases.streamer

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.StreamerParams
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.presentation.mapper.StreamerViewMapper
import net.grandcentrix.thirtyinch.TiPresenter
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class StreamerPresenter @Inject constructor(private val useCase: SingleUseCase<List<Streamer>, StreamerParams>,
                                                 private val mapper: StreamerViewMapper)
    : TiPresenter<StreamerContract>() {

}