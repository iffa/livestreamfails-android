package digital.sogood.livestreamfails.data.repository.fail

import digital.sogood.livestreamfails.data.model.FailEntity
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailRemote {
    fun getFails(page: Int?): Single<List<FailEntity>>
}