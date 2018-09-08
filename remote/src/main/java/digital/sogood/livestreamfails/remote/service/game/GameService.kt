package digital.sogood.livestreamfails.remote.service.game

import digital.sogood.livestreamfails.remote.model.GameModel
import io.reactivex.Single
import okhttp3.OkHttpClient

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameService(private val okHttpClient: OkHttpClient) {
    fun getGames(page: Int?): Single<List<GameModel>> {
        TODO("Not implemented")
    }
}