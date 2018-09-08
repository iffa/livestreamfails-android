package digital.sogood.livestreamfails.data

import digital.sogood.livestreamfails.data.mapper.FailMapper
import digital.sogood.livestreamfails.data.source.FailDataStoreFactory
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.repository.FailRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [FailRepository] interface for communicating to and from
 * data sources.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailDataRepository @Inject constructor(private val factory: FailDataStoreFactory,
                                             private val mapper: FailMapper)
    : FailRepository {
    override fun getFails(page: Int?): Single<List<Fail>> {
        val dataStore = factory.getDataStore()

        return dataStore.getFails(page)
                .map { list ->
                    list.map { listItem ->
                        mapper.mapFromEntity(listItem)
                    }
                }
    }
}