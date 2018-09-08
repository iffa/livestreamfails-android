package digital.sogood.livestreamfails.remote.service.streamer

import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import digital.sogood.livestreamfails.remote.mapper.StreamerEntityMapper
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerRemoteImpl @Inject constructor(private val failService: StreamerService,
                                             private val entityMapper: StreamerEntityMapper)
    : StreamerRemote {
}