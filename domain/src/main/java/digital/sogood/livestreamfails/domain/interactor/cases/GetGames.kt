package digital.sogood.livestreamfails.domain.interactor.cases

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.repository.GameRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [Game] instances from the [GameRepository].
 */
open class GetGames @Inject constructor(private val repository: GameRepository,
                                        threadExecutor: ThreadExecutor,
                                        postExecutionThread: PostExecutionThread)
    : SingleUseCase<List<Game>, GameParams>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: GameParams?): Single<List<Game>> {
        return repository.getGames(params?.page)
    }
}

data class GameParams(val page: Int? = 0)