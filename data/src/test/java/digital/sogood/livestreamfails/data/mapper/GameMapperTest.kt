package digital.sogood.livestreamfails.data.mapper

import digital.sogood.livestreamfails.data.model.GameEntity
import digital.sogood.livestreamfails.data.test.factory.GameFactory
import digital.sogood.livestreamfails.domain.model.Game
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameMapperTest {
    private val mapper = GameMapper()

    @Test
    fun mapFromEntityTest() {
        val entity = GameFactory.makeGameEntity()
        val item = mapper.mapFromEntity(entity)

        assertDataEquality(entity, item)
    }

    @Test
    fun mapToEntityTest() {
        val item = GameFactory.makeGame()
        val entity = mapper.mapToEntity(item)

        assertDataEquality(entity, item)
    }

    private fun assertDataEquality(entity: GameEntity,
                                   item: Game) {
        assertEquals(entity.name, item.name)
        assertEquals(entity.fails, item.fails)
        assertEquals(entity.imageUrl, item.imageUrl)
    }
}