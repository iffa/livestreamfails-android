package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.remote.test.factory.DetailsFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsEntityMapperTest {
    private val mapper = DetailsEntityMapper()

    @Test
    fun mapFromRemoteTest() {
        val model = DetailsFactory.makeDetailsModel()
        val entity = mapper.mapFromRemote(model)

        assertEquals(model.title, entity.title)
        assertEquals(model.game, entity.game)
        assertEquals(model.streamer, entity.streamer)
        assertEquals(model.points, entity.points)
        assertEquals(model.nsfw, entity.nsfw)
        assertEquals(model.videoUrl, entity.videoUrl)
        assertEquals(model.sourceUrl, entity.sourceUrl)
    }
}