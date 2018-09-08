package digital.sogood.livestreamfails.data

import digital.sogood.livestreamfails.data.mapper.GameMapper
import digital.sogood.livestreamfails.data.source.GameDataStoreFactory
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.repository.GameRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameDataRepository @Inject constructor(private val factory: GameDataStoreFactory,
                                             private val mapper: GameMapper)
    : GameRepository {
    override fun getGames(page: Int?): Single<List<Game>> {
        TODO("Not implemented")
    }
}