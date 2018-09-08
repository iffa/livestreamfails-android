package digital.sogood.livestreamfails.domain.repository

import digital.sogood.livestreamfails.domain.model.Fail
import io.reactivex.Single

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented.
 */
interface FailRepository {
    fun getFails(page: Int?): Single<List<Fail>>
}