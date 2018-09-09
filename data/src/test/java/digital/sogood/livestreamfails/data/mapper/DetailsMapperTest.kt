package digital.sogood.livestreamfails.data.mapper

import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.data.test.factory.DetailsFactory
import digital.sogood.livestreamfails.domain.model.Details
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsMapperTest {
    private val mapper = DetailsMapper()

    @Test
    fun mapFromEntityTest() {
        val entity = DetailsFactory.makeDetailsEntity()
        val item = mapper.mapFromEntity(entity)

        assertDataEquality(entity, item)
    }

    @Test
    fun mapToEntityTest() {
        val item = DetailsFactory.makeDetails()
        val entity = mapper.mapToEntity(item)

        assertDataEquality(entity, item)
    }

    private fun assertDataEquality(entity: DetailsEntity,
                                   item: Details) {
        assertEquals(entity.title, item.title)
        assertEquals(entity.game, item.game)
        assertEquals(entity.streamer, item.streamer)
        assertEquals(entity.points, item.points)
        assertEquals(entity.nsfw, item.nsfw)
        assertEquals(entity.sourceUrl, item.sourceUrl)
        assertEquals(entity.videoUrl, item.videoUrl)
    }
}