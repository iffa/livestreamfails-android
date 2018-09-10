package digital.sogood.livestreamfails.presentation.mapper

import digital.sogood.livestreamfails.presentation.test.factory.DetailsFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsViewMapperTest {
    private val mapper = DetailsViewMapper()

    @Test
    fun mapToViewTest() {
        val model = DetailsFactory.makeDetails()
        val entity = mapper.mapToView(model)

        assertEquals(model.title, entity.title)
        assertEquals(model.game, entity.game)
        assertEquals(model.streamer, entity.streamer)
        assertEquals(model.points, entity.points)
        assertEquals(model.nsfw, entity.nsfw)
        assertEquals(model.videoUrl, entity.videoUrl)
        assertEquals(model.sourceUrl, entity.sourceUrl)
    }
}