package digital.sogood.livestreamfails.remote.service.details

import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.data.repository.details.DetailsRemote
import digital.sogood.livestreamfails.remote.mapper.DetailsEntityMapper
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsRemoteImpl @Inject constructor(private val service: DetailsService,
                                            private val entityMapper: DetailsEntityMapper)
    : DetailsRemote {
    override fun getDetails(postId: Long?): Single<DetailsEntity> {
        return service.getDetails(postId)
                .map {
                    entityMapper.mapFromRemote(it)
                }
    }
}