package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.remote.model.FailModel
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailService {
    fun getFails(page: Int?, timeFrame: TimeFrame?, order: Order?, nsfw: Boolean?, game: String?, streamer: String?): Single<List<FailModel>>
}