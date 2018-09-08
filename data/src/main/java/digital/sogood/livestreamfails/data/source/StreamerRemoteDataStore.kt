package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.repository.streamer.StreamerDataStore
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class StreamerRemoteDataStore @Inject constructor(private val streamerRemote: StreamerRemote)
    : StreamerDataStore

/**
 * Creates an instance of a [StreamerDataStore].
 */
open class StreamerDataStoreFactory @Inject constructor(private val remoteDataStore: StreamerRemoteDataStore) {
    open fun getDataStore(): StreamerDataStore {
        return remoteDataStore
    }
}