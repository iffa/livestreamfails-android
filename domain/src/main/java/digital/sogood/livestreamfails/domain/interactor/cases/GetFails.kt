package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.domain.repository.FailRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Fail] instances from the [FailRepository].
 */
open class GetFails @Inject constructor(private val repository: FailRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<Fail>, FailParams>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: FailParams): Single<List<Fail>> {
        return repository.getFails(params.page, params.timeFrame, params.order,
                params.nsfw, params.game, params.streamer)
    }
}

/**
 * Parameters for the get fails -request. To get the "front page", don't pass a [game] or [streamer]
 * parameter. Only pass [game] or [streamer], not both.
 */
data class FailParams(val page: Int = 0,
                      val timeFrame: TimeFrame = TimeFrame.DAY,
                      val order: Order = Order.HOT,
                      val nsfw: Boolean = false,
                      val game: String = "",
                      val streamer: String = "") {
    fun equalsIgnorePage(params: FailParams): Boolean {
        return params.timeFrame == timeFrame
                && params.order == order
                && params.nsfw == nsfw
                && params.game == game
                && params.streamer == streamer
    }
}