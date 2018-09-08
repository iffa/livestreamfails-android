package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.repository.game.GameDataStore
import digital.sogood.livestreamfails.data.repository.game.GameRemote
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class GameRemoteDataStore @Inject constructor(private val gameRemote: GameRemote)
    : GameDataStore

/**
 * Creates an instance of a [GameDataStore].
 */
open class GameDataStoreFactory @Inject constructor(private val remoteDataStore: GameRemoteDataStore) {
    open fun getDataStore(): GameDataStore {
        return remoteDataStore
    }
}