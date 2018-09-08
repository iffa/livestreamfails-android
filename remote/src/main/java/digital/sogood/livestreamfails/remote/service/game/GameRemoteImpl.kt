package digital.sogood.livestreamfails.remote.service.game

import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.repository.game.GameRemote
import digital.sogood.livestreamfails.remote.mapper.GameEntityMapper
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameRemoteImpl @Inject constructor(private val service: GameService,
                                         private val entityMapper: GameEntityMapper)
    : GameRemote {
    override fun getGames(page: Int?): Single<List<GameEntity>> {
        return service.getGames(page)
                .map {
                    it.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }
}