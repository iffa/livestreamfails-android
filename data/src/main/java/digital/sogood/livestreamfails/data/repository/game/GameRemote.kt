package digital.sogood.livestreamfails.data.repository.game

import digital.sogood.livestreamfails.data.model.GameEntity
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface GameRemote {
    fun getGames(page: Int?): Single<List<GameEntity>>
}