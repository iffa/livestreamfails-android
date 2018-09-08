package digital.sogood.livestreamfails.data.mapper

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.model.Streamer
import javax.inject.Inject

open class GameMapper @Inject constructor() : Mapper<GameEntity, Game> {
    override fun mapFromEntity(type: GameEntity): Game {
        return Game(type.name, type.fails, type.imageUrl)
    }

    override fun mapToEntity(type: Game): GameEntity {
        return GameEntity(type.name, type.fails, type.imageUrl)
    }
}

open class StreamerMapper @Inject constructor() : Mapper<StreamerEntity, Streamer> {
    override fun mapFromEntity(type: StreamerEntity): Streamer {
        return Streamer(type.name, type.fails, type.imageUrl)
    }

    override fun mapToEntity(type: Streamer): StreamerEntity {
        return StreamerEntity(type.name, type.fails, type.imageUrl)
    }
}

open class FailMapper @Inject constructor() : Mapper<FailEntity, Fail> {
    override fun mapFromEntity(type: FailEntity): Fail {
        return Fail(type.title, type.streamer, type.game, type.points, type.nsfw, type.thumbnailUrl)
    }

    override fun mapToEntity(type: Fail): FailEntity {
        return FailEntity(type.title, type.streamer, type.game, type.points, type.nsfw, type.thumbnailUrl)
    }
}