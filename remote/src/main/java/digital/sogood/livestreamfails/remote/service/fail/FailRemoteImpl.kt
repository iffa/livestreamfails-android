package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.remote.mapper.FailEntityMapper
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailRemoteImpl @Inject constructor(private val failService: FailService,
                                         private val entityMapper: FailEntityMapper)
    : FailRemote {
}