package digital.sogood.livestreamfails.remote.service.details

import digital.sogood.livestreamfails.remote.model.DetailsModel
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface DetailsService {
    fun getDetails(postId: Long): Single<DetailsModel>
}