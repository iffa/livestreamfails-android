package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.data.repository.streamer.StreamerDataStore
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class StreamerRemoteDataStore @Inject constructor(private val remote: StreamerRemote)
    : StreamerDataStore {
    override fun getStreamers(page: Int?): Single<List<StreamerEntity>> {
        return remote.getStreamers(page)
    }
}

/**
 * Creates an instance of a [StreamerDataStore].
 */
open class StreamerDataStoreFactory @Inject constructor(private val remoteDataStore: StreamerRemoteDataStore) {
    open fun getDataStore(): StreamerDataStore {
        return remoteDataStore
    }
}