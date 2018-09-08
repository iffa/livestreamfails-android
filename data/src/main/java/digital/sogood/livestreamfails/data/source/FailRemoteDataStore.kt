package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.repository.fail.FailDataStore
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailRemoteDataStore @Inject constructor(private val failRemote: FailRemote)
    : FailDataStore

/**
 * Creates an instance of a [FailDataStore].
 */
open class FailDataStoreFactory @Inject constructor(private val remoteDataStore: FailRemoteDataStore) {
    open fun getDataStore(): FailDataStore {
        return remoteDataStore
    }
}