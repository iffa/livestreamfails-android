package digital.sogood.livestreamfails.domain.repository

import digital.sogood.livestreamfails.domain.model.Streamer
import io.reactivex.Single

interface StreamerRepository {
    fun getStreamers(page: Int? = 0): Single<List<Streamer>>
}