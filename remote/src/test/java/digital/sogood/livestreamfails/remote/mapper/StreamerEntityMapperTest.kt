package digital.sogood.livestreamfails.remote.mapper

import digital.sogood.livestreamfails.remote.test.factory.StreamerFactory
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerEntityMapperTest {
    private val mapper = StreamerEntityMapper()

    @Test
    fun mapFromRemoteTest() {
        val model = StreamerFactory.makeStreamerModel()
        val entity = mapper.mapFromRemote(model)

        assertEquals(model.name, entity.name)
        assertEquals(model.fails, entity.fails)
        assertEquals(model.imageUrl, entity.imageUrl)
    }
}