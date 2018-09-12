package digital.sogood.livestreamfails.mobile.mapper

import digital.sogood.livestreamfails.mobile.model.DetailsViewModel
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.model.GameViewModel
import digital.sogood.livestreamfails.mobile.model.StreamerViewModel
import digital.sogood.livestreamfails.presentation.model.DetailsView
import digital.sogood.livestreamfails.presentation.model.FailView
import digital.sogood.livestreamfails.presentation.model.GameView
import digital.sogood.livestreamfails.presentation.model.StreamerView
import javax.inject.Inject

/**
 * Map a [FailView] to a [FailViewModel] instance.
 */
open class FailViewModelMapper @Inject constructor() : Mapper<FailViewModel, FailView> {
    override fun mapToViewModel(type: FailView): FailViewModel {
        return FailViewModel(type.title, type.streamer, type.game, type.points, type.nsfw, type.thumbnailUrl, type.detailsUrl)
    }
}

/**
 * Map a [GameView] to a [GameViewModel] instance.
 */
open class GameViewModelMapper @Inject constructor() : Mapper<GameViewModel, GameView> {
    override fun mapToViewModel(type: GameView): GameViewModel {
        return GameViewModel(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [StreamerView] to a [StreamerViewModel] instance.
 */
open class StreamerViewModelMapper @Inject constructor() : Mapper<StreamerViewModel, StreamerView> {
    override fun mapToViewModel(type: StreamerView): StreamerViewModel {
        return StreamerViewModel(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [DetailsView] to a [DetailsViewModel] instance.
 */
open class DetailsViewModelMapper @Inject constructor() : Mapper<DetailsViewModel, DetailsView> {
    override fun mapToViewModel(type: DetailsView): DetailsViewModel {
        return DetailsViewModel(type.title, type.streamer, type.game, type.points, type.nsfw, type.videoUrl, type.sourceUrl)
    }
}