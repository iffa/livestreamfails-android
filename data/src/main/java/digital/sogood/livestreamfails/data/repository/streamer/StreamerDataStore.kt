package digital.sogood.livestreamfails.data.repository.streamer

import digital.sogood.livestreamfails.data.model.StreamerEntity
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface StreamerDataStore {
    fun getStreamers(page: Int?): Single<List<StreamerEntity>>
}