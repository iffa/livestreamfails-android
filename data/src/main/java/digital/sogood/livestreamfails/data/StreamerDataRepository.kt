package digital.sogood.livestreamfails.data

import digital.sogood.livestreamfails.data.mapper.StreamerMapper
import digital.sogood.livestreamfails.data.source.StreamerDataStoreFactory
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Provides an implementation of the [StreamerRepository] interface for communicating to and from
 * data sources.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerDataRepository @Inject constructor(private val factory: StreamerDataStoreFactory,
                                                 private val mapper: StreamerMapper)
    : StreamerRepository {
    override fun getStreamers(page: Int?): Single<List<Streamer>> {
        val dataStore = factory.getDataStore()

        return dataStore.getStreamers(page)
                .map { list ->
                    list.map { listItem ->
                        mapper.mapFromEntity(listItem)
                    }
                }
    }
}