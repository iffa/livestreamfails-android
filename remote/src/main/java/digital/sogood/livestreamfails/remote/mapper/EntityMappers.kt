package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.remote.model.FailModel
import digital.sogood.livestreamfails.remote.model.GameModel
import digital.sogood.livestreamfails.remote.model.StreamerModel
import javax.inject.Inject

open class GameEntityMapper @Inject constructor() : EntityMapper<GameModel, GameEntity> {
    override fun mapFromRemote(type: GameModel): GameEntity {
        TODO("Not implemented")
    }
}

open class StreamerEntityMapper @Inject constructor() : EntityMapper<StreamerModel, StreamerEntity> {
    override fun mapFromRemote(type: StreamerModel): StreamerEntity {
        TODO("Not implemented")
    }
}

open class FailEntityMapper @Inject constructor() : EntityMapper<FailModel, FailEntity> {
    override fun mapFromRemote(type: FailModel): FailEntity {
        TODO("Not implemented")
    }
}