package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.remote.model.FailModel
import io.reactivex.Single
import okhttp3.OkHttpClient

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailService(private val okHttpClient: OkHttpClient) {
    fun getFails(page: Int?): Single<List<FailModel>> {
        TODO("Not implemented")
    }
}