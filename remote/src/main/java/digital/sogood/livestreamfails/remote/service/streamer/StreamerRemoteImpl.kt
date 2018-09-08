package digital.sogood.livestreamfails.remote.service.streamer

import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import digital.sogood.livestreamfails.remote.mapper.StreamerEntityMapper
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerRemoteImpl @Inject constructor(private val service: StreamerService,
                                             private val entityMapper: StreamerEntityMapper)
    : StreamerRemote {
    override fun getStreamers(page: Int?): Single<List<StreamerEntity>> {
        return service.getStreamers(page)
                .map {
                    it.map { listItem ->
                        entityMapper.mapFromRemote(listItem)
                    }
                }
    }
}