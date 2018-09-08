package digital.sogood.livestreamfails.data.mapper

import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.data.test.factory.StreamerFactory
import digital.sogood.livestreamfails.domain.model.Streamer
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerMapperTest {
    private val mapper = StreamerMapper()

    @Test
    fun mapFromEntityTest() {
        val entity = StreamerFactory.makeStreamerEntity()
        val item = mapper.mapFromEntity(entity)

        assertDataEquality(entity, item)
    }

    @Test
    fun mapToEntityTest() {
        val item = StreamerFactory.makeStreamer()
        val entity = mapper.mapToEntity(item)

        assertDataEquality(entity, item)
    }

    private fun assertDataEquality(entity: StreamerEntity,
                                   item: Streamer) {
        assertEquals(entity.name, item.name)
        assertEquals(entity.fails, item.fails)
        assertEquals(entity.imageUrl, item.imageUrl)
    }
}