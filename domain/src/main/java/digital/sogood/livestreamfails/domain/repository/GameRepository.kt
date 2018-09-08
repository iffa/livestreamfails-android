package digital.sogood.livestreamfails.domain.repository

import digital.sogood.livestreamfails.domain.model.Game
import io.reactivex.Single

interface GameRepository {
    fun getGames(page: Int? = 0): Single<List<Game>>
}