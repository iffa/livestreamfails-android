package digital.sogood.livestreamfails.data

import digital.sogood.livestreamfails.data.mapper.DetailsMapper
import digital.sogood.livestreamfails.data.source.DetailsDataStoreFactory
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [DetailsRepository] interface for communicating to and from
 * data sources.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsDataRepository @Inject constructor(private val factory: DetailsDataStoreFactory,
                                                private val mapper: DetailsMapper)
    : DetailsRepository {
    override fun getDetails(postId: Long?): Single<Details> {
        val dataStore = factory.getDataStore()

        return dataStore.getDetails(postId)
                .map {
                    mapper.mapFromEntity(it)
                }
    }
}