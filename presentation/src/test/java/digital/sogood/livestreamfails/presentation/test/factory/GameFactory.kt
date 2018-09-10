package digital.sogood.livestreamfails.presentation.test.factory

import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.presentation.model.GameView
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameFactory {
    companion object Factory {
        fun makeGameView(): GameView {
            return GameView(randomUuid(), randomInt(), randomUuid())
        }

        fun makeGame(): Game {
            return Game(randomUuid(), randomInt(), randomUuid())
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