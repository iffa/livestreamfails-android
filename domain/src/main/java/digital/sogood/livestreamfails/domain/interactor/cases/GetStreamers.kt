package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Streamer] instances from the [StreamerRepository].
 */
open class GetStreamers @Inject constructor(val repository: StreamerRepository,
                                            threadExecutor: ThreadExecutor,
                                            postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<Streamer>, Int>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Int?): Single<List<Streamer>> {
        return repository.getStreamers(params)
    }
}