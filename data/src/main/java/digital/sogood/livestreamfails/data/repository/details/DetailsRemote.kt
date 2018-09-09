package digital.sogood.livestreamfails.data.repository.details

import digital.sogood.livestreamfails.data.model.DetailsEntity
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface DetailsRemote {
    fun getDetails(postId: Long?): Single<DetailsEntity>
}