package digital.sogood.livestreamfails.data.repository.fail

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailRemote {
    fun getFails(page: Int?, timeFrame: TimeFrame?, order: Order?,
                 nsfw: Boolean?, game: String?, streamer: String?): Single<List<FailEntity>>
}