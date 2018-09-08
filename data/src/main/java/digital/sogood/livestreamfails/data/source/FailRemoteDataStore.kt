package digital.sogood.livestreamfails.data.source

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.repository.fail.FailDataStore
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailRemoteDataStore @Inject constructor(private val remote: FailRemote)
    : FailDataStore {
    override fun getFails(page: Int?): Single<List<FailEntity>> {
        return remote.getFails(page)
    }
}

/**
 * Creates an instance of a [FailDataStore].
 */
open class FailDataStoreFactory @Inject constructor(private val remoteDataStore: FailRemoteDataStore) {
    open fun getDataStore(): FailDataStore {
        return remoteDataStore
    }
}