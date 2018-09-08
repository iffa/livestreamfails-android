package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.remote.test.factory.FailFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailEntityMapperTest {
    private val mapper = FailEntityMapper()

    @Test
    fun mapFromRemoteTest() {
        val model = FailFactory.makeFailModel()
        val entity = mapper.mapFromRemote(model)

        assertEquals(model.title, entity.title)
        assertEquals(model.game, entity.game)
        assertEquals(model.streamer, entity.streamer)
        assertEquals(model.points, entity.points)
        assertEquals(model.nsfw, entity.nsfw)
        assertEquals(model.thumbnailUrl, entity.thumbnailUrl)
    }
}