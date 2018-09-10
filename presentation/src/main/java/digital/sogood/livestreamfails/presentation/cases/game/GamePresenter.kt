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
    override fun onCreate() {
        super.onCreate()

        retrieveGames()
    }

    override fun onDestroy() {
        super.onDestroy()

        useCase.dispose()
    }

    fun retrieveGames() {
        deliverToView {
            showProgress()
        }

        useCase.execute(Subscriber())
    }

    internal fun handleGetGamesSuccess(games: List<Game>) {
        deliverToView {
            hideProgress()
            hideErrorState()

            if (games.isNotEmpty()) {
                hideEmptyState()
                showGames(games.map { mapper.mapToView(it) })
            } else {
                hideGames()
                showEmptyState()
            }
        }
    }

    inner class Subscriber : DisposableSingleObserver<List<Game>>() {
        override fun onSuccess(t: List<Game>) = handleGetGamesSuccess(t)

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