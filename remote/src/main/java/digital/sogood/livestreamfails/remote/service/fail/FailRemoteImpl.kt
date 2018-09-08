package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.remote.mapper.FailEntityMapper
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailRemoteImpl @Inject constructor(private val service: FailService,
                                         private val entityMapper: FailEntityMapper)
    : FailRemote {
    override fun getFails(page: Int?, timeFrame: TimeFrame?, order: Order?, nsfw: Boolean?, game: String?, streamer: String?): Single<List<FailEntity>> {
        return service.getFails(page, timeFrame, order, nsfw, game, streamer)
                .map {
                    it.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }
}