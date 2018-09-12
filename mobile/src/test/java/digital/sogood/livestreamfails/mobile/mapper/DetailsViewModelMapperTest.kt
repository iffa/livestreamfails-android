package digital.sogood.livestreamfails.mobile.mapper

import digital.sogood.livestreamfails.mobile.test.factory.DetailsFactory
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsViewModelMapperTest {
    private val mapper = DetailsViewModelMapper()

    @Test
    fun mapToViewTest() {
        val item = DetailsFactory.makeDetailsView()
        val mapped = mapper.mapToViewModel(item)

        assertEquals(item.title, mapped.title)
        assertEquals(item.game, mapped.game)
        assertEquals(item.streamer, mapped.streamer)
        assertEquals(item.points, mapped.points)
        assertEquals(item.nsfw, mapped.nsfw)
        assertEquals(item.videoUrl, mapped.videoUrl)
        assertEquals(item.sourceUrl, mapped.sourceUrl)
    }
}