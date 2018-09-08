package digital.sogood.livestreamfails.domain.test.factory

import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for [Game] related instances
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameFactory {
    companion object Factory {
        fun makeGameList(count: Int): List<Game> {
            val games = mutableListOf<Game>()
            repeat(count) {
                games.add(makeGame())
            }
            return games
        }

        fun makeGame(): Game {
            return Game(randomUuid(), randomInt(), randomUuid())
        }
    }
}