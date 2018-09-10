package digital.sogood.livestreamfails.presentation.mapper

import digital.sogood.livestreamfails.presentation.test.factory.GameFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameViewMapperTest {
    private val mapper = GameViewMapper()

    @Test
    fun mapToViewTest() {
        val model = GameFactory.makeGame()
        val entity = mapper.mapToView(model)

        assertEquals(model.name, entity.name)
        assertEquals(model.fails, entity.fails)
        assertEquals(model.imageUrl, entity.imageUrl)
    }
}