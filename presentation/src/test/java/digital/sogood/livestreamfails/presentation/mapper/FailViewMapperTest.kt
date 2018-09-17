package digital.sogood.livestreamfails.presentation.mapper

import digital.sogood.livestreamfails.presentation.test.factory.FailFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailViewMapperTest {
    private val mapper = FailViewMapper()

    @Test
    fun mapToViewTest() {
        val model = FailFactory.makeFail()
        val entity = mapper.mapToView(model)

        assertEquals(model.title, entity.title)
        assertEquals(model.game, entity.game)
        assertEquals(model.streamer, entity.streamer)
        assertEquals(model.points, entity.points)
        assertEquals(model.nsfw, entity.nsfw)
        assertEquals(model.thumbnailUrl, entity.thumbnailUrl)
        assertEquals(model.postId, entity.postId)
    }
}