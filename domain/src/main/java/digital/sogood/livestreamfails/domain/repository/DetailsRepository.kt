package digital.sogood.livestreamfails.domain.repository

import digital.sogood.livestreamfails.domain.model.Details
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface DetailsRepository {
    fun getDetails(postId: Long? = 0): Single<Details>
}