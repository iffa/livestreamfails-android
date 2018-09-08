package digital.sogood.livestreamfails.remote.service.streamer

import digital.sogood.livestreamfails.remote.model.StreamerModel
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface StreamerService {
    fun getStreamers(page: Int? = 0): Single<List<StreamerModel>>
}