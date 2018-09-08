package digital.sogood.livestreamfails.remote.service.game

import digital.sogood.livestreamfails.remote.model.GameModel
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface GameService {
    fun getGames(page: Int? = 0): Single<List<GameModel>>
}