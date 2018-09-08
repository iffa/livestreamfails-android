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
        TODO("Not implemented")
    }

    override fun mapToEntity(type: Game): GameEntity {
        TODO("Not implemented")
    }
}

open class StreamerMapper @Inject constructor() : Mapper<StreamerEntity, Streamer> {
    override fun mapFromEntity(type: StreamerEntity): Streamer {
        TODO("Not implemented")
    }

    override fun mapToEntity(type: Streamer): StreamerEntity {
        TODO("Not implemented")
    }
}

open class FailMapper @Inject constructor() : Mapper<FailEntity, Fail> {
    override fun mapFromEntity(type: FailEntity): Fail {
        TODO("Not implemented")
    }

    override fun mapToEntity(type: Fail): FailEntity {
        TODO("Not implemented")
    }
}