package digital.sogood.livestreamfails.data.mapper

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.test.factory.FailFactory
import digital.sogood.livestreamfails.domain.model.Fail
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailMapperTest {
    private val mapper = FailMapper()

    @Test
    fun mapFromEntityTest() {
        val entity = FailFactory.makeFailEntity()
        val item = mapper.mapFromEntity(entity)

        assertDataEquality(entity, item)
    }

    @Test
    fun mapToEntityTest() {
        val item = FailFactory.makeFail()
        val entity = mapper.mapToEntity(item)

        assertDataEquality(entity, item)
    }

    private fun assertDataEquality(entity: FailEntity,
                                   item: Fail) {
        assertEquals(entity.title, item.title)
        assertEquals(entity.game, item.game)
        assertEquals(entity.streamer, item.streamer)
        assertEquals(entity.points, item.points)
        assertEquals(entity.nsfw, item.nsfw)
        assertEquals(entity.thumbnailUrl, item.thumbnailUrl)
        assertEquals(entity.postId, item.postId)
    }
}