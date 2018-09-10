package digital.sogood.livestreamfails.presentation.mapper

import digital.sogood.livestreamfails.presentation.test.factory.StreamerFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerViewMapperTest {
    private val mapper = StreamerViewMapper()

    @Test
    fun mapToViewTest() {
        val model = StreamerFactory.makeStreamer()
        val entity = mapper.mapToView(model)

        assertEquals(model.name, entity.name)
        assertEquals(model.fails, entity.fails)
        assertEquals(model.imageUrl, entity.imageUrl)
    }
}