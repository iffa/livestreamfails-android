package digital.sogood.livestreamfails.domain.repository

import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import io.reactivex.Single

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented.
 */
interface FailRepository {
    fun getFails(
            page: Int? = 0,
            timeFrame: TimeFrame? = TimeFrame.DAY,
            order: Order? = Order.HOT,
            nsfw: Boolean? = false,
            game: String? = "",
            streamer: String? = ""
    ): Single<List<Fail>>
}