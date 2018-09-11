package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retrieving [Details] instances from the [DetailsRepository].
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class GetDetails @Inject constructor(private val repository: DetailsRepository,
                                          threadExecutor: ThreadExecutor,
                                          postExecutionThread: PostExecutionThread)
    : SingleUseCase<Details, DetailsParams>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: DetailsParams): Single<Details> {
        return repository.getDetails(params.postId)
    }
}

data class DetailsParams(val postId: Long)