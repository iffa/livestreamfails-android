package digital.sogood.livestreamfails.mobile.test.factory

import digital.sogood.livestreamfails.mobile.model.GameViewModel
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.presentation.model.GameView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameFactory {
    companion object Factory {
        fun makeGameView(): GameView {
            return GameView(randomUuid(), randomInt(), randomUuid())
        }

        fun makeGameViewModel(): GameViewModel {
            return GameViewModel(randomUuid(), randomInt(), randomUuid())
        }

        fun makeGameViewModelList(count: Int): List<GameViewModel> {
            val entities = mutableListOf<GameViewModel>()
            repeat(count) {
                entities.add(makeGameViewModel())
            }
            return entities
        }

        fun makeGameViewList(count: Int): List<GameView> {
            val entities = mutableListOf<GameView>()
            repeat(count) {
                entities.add(makeGameView())
            }
            return entities
        }
    }
}