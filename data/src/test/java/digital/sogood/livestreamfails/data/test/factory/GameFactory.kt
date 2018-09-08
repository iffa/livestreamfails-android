package digital.sogood.livestreamfails.data.test.factory

import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.domain.model.Game

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameFactory {
    companion object Factory {

        fun makeGameEntity(): GameEntity {
            return GameEntity(DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomUuid())
        }

        fun makeGame(): Game {
            return Game(DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomUuid())
        }

        fun makeGameEntityList(count: Int): List<GameEntity> {
            val entities = mutableListOf<GameEntity>()
            repeat(count) {
                entities.add(makeGameEntity())
            }
            return entities
        }

        fun makeGameList(count: Int): List<Game> {
            val items = mutableListOf<Game>()
            repeat(count) {
                items.add(makeGame())
            }
            return items
        }
    }
}