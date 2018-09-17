package digital.sogood.livestreamfails.presentation.mapper

import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.presentation.model.DetailsView
import digital.sogood.livestreamfails.presentation.model.FailView
import digital.sogood.livestreamfails.presentation.model.GameView
import digital.sogood.livestreamfails.presentation.model.StreamerView
import javax.inject.Inject

/**
 * Map a [Fail] to a [FailView] instance.
 */
open class FailViewMapper @Inject constructor() : Mapper<FailView, Fail> {
    override fun mapToView(type: Fail): FailView {
        return FailView(type.title, type.streamer, type.game, type.points, type.nsfw, type.thumbnailUrl, type.postId)
    }
}

/**
 * Map a [Game] to a [GameView] instance.
 */
open class GameViewMapper @Inject constructor() : Mapper<GameView, Game> {
    override fun mapToView(type: Game): GameView {
        return GameView(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [Streamer] to a [StreamerView] instance.
 */
open class StreamerViewMapper @Inject constructor() : Mapper<StreamerView, Streamer> {
    override fun mapToView(type: Streamer): StreamerView {
        return StreamerView(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [Details] to a [DetailsView] instance.
 */
open class DetailsViewMapper @Inject constructor() : Mapper<DetailsView, Details> {
    override fun mapToView(type: Details): DetailsView {
        return DetailsView(type.title, type.streamer, type.game, type.points, type.nsfw, type.videoUrl, type.sourceUrl)
    }
}