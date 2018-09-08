package digital.sogood.livestreamfails.remote.service.streamer

import digital.sogood.livestreamfails.remote.model.StreamerModel
import io.reactivex.Single
import okhttp3.OkHttpClient

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerService(private val okHttpClient: OkHttpClient) {
    fun getStreamers(page: Int?): Single<List<StreamerModel>> {
        TODO("Not implemented")
    }
}