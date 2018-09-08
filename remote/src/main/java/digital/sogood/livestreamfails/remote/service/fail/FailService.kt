package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.remote.model.FailModel
import io.reactivex.Single

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface FailService {
    fun getFails(page: Int? = 0): Single<List<FailModel>>
}