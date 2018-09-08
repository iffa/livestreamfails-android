package digital.sogood.livestreamfails.data

import digital.sogood.livestreamfails.data.mapper.FailMapper
import digital.sogood.livestreamfails.data.source.FailDataStoreFactory
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.repository.FailRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailDataRepository @Inject constructor(private val factory: FailDataStoreFactory,
                                             private val mapper: FailMapper)
    : FailRepository {
    override fun getFails(page: Int?): Single<List<Fail>> {
        TODO("Not implemented")
    }
}