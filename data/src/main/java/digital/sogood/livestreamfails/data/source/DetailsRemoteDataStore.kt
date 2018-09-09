package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.data.repository.details.DetailsDataStore
import digital.sogood.livestreamfails.data.repository.details.DetailsRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class DetailsRemoteDataStore @Inject constructor(private val remote: DetailsRemote) : DetailsDataStore {
    override fun getDetails(postId: Long?): Single<DetailsEntity> {
        return remote.getDetails(postId)
    }
}

/**
 * Creates an instance of a [DetailsDataStore].
 */
open class DetailsDataStoreFactory @Inject constructor(private val remoteDataStore: DetailsRemoteDataStore) {
    open fun getDataStore(): DetailsDataStore {
        return remoteDataStore
    }
}