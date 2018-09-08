package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.remote.test.factory.GameFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameEntityMapperTest {
    private val mapper = GameEntityMapper()

    @Test
    fun mapFromRemoteTest() {
        val model = GameFactory.makeGameModel()
        val entity = mapper.mapFromRemote(model)

        assertEquals(model.name, entity.name)
        assertEquals(model.fails, entity.fails)
        assertEquals(model.imageUrl, entity.imageUrl)
    }
}