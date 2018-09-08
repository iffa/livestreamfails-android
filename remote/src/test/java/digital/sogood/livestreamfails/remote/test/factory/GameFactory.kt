package digital.sogood.livestreamfails.remote.test.factory

import digital.sogood.livestreamfails.remote.model.GameModel
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameFactory {
    companion object Factory {

        fun makeGameModel(): GameModel {
            return GameModel(randomUuid(), randomInt(), randomUuid())
        }

        fun makeGameModelList(count: Int): List<GameModel> {
            val entities = mutableListOf<GameModel>()
            repeat(count) {
                entities.add(makeGameModel())
            }
            return entities
        }
    }
}