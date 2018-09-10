package digital.sogood.livestreamfails.presentation.cases.game

import digital.sogood.livestreamfails.domain.interactor.SingleUseCase
import digital.sogood.livestreamfails.domain.interactor.cases.GameParams
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.presentation.mapper.GameViewMapper
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.TiPresenter
import net.grandcentrix.thirtyinch.kotlin.deliverToView
import javax.inject.Inject

/**
 * TODO: Pagination support
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
open class GamePresenter @Inject constructor(private val useCase: SingleUseCase<List<Game>, GameParams>,
                                             private val mapper: GameViewMapper)
    : TiPresenter<GameContract>() {
    internal var currentPage = -1

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveGames() {
        currentPage++

        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber(), GameParams(currentPage))
    }

    internal fun handleSuccess(games: List<Game>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (games.isNotEmpty()) {
                hideEmptyState()
                showGames(games.map { mapper.mapToView(it) })
            } else {
                if (currentPage > 0) {
                    showNoMoreResultsState()
                } else {
                    hideGames()
                    showEmptyState()
                }
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Game>>() {
        override fun onSuccess(t: List<Game>) = handleSuccess(t)

        /**
         * TODO: When encountering an error, the implementing party should have the option of retrying the request, if we are on page 10 for example. We don't want to lose all previous results.
         */
        override fun onError(e: Throwable) {
            deliverToView {
                hideProgress()
                hideGames()
                hideEmptyState()
                showErrorState()
            }
        }
    }
}