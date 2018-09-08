package digital.sogood.livestreamfails.remote.service.game

import digital.sogood.livestreamfails.data.repository.game.GameRemote
import digital.sogood.livestreamfails.remote.mapper.GameEntityMapper
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameRemoteImpl @Inject constructor(private val failService: GameService,
                                         private val entityMapper: GameEntityMapper)
    : GameRemote {
}