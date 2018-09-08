package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.repository.FailRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Fail] instances from the [FailRepository].
 */
open class GetFails @Inject constructor(val repository: FailRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<Fail>, Int>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Int?): Single<List<Fail>> {
        return repository.getFails(params)
    }
}