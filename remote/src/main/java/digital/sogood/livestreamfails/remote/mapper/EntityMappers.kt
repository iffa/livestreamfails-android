package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.remote.model.FailModel
import digital.sogood.livestreamfails.remote.model.GameModel
import digital.sogood.livestreamfails.remote.model.StreamerModel
import javax.inject.Inject

/**
 * Map a [GameModel] to a [GameEntity] instance.
 */
open class GameEntityMapper @Inject constructor() : EntityMapper<GameModel, GameEntity> {
    override fun mapFromRemote(type: GameModel): GameEntity {
        return GameEntity(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [StreamerModel] to a [StreamerEntity] instance.
 */
open class StreamerEntityMapper @Inject constructor() : EntityMapper<StreamerModel, StreamerEntity> {
    override fun mapFromRemote(type: StreamerModel): StreamerEntity {
        return StreamerEntity(type.name, type.fails, type.imageUrl)
    }
}

/**
 * Map a [FailModel] to a [FailEntity] instance.
 */
open class FailEntityMapper @Inject constructor() : EntityMapper<FailModel, FailEntity> {
    override fun mapFromRemote(type: FailModel): FailEntity {
        return FailEntity(type.title, type.streamer, type.game, type.points, type.nsfw, type.thumbnailUrl)
    }
}