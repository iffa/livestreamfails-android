package digital.sogood.livestreamfails.mobile.mapper

import digital.sogood.livestreamfails.mobile.test.factory.FailFactory
import org.junit.Assert
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailViewModelMapperTest {
    private val mapper = FailViewModelMapper()

    @Test
    fun mapToViewTest() {
        val item = FailFactory.makeFailView()
        val mapped = mapper.mapToViewModel(item)

        Assert.assertEquals(item.title, mapped.title)
        Assert.assertEquals(item.game, mapped.game)
        Assert.assertEquals(item.streamer, mapped.streamer)
        Assert.assertEquals(item.points, mapped.points)
        Assert.assertEquals(item.nsfw, mapped.nsfw)
        Assert.assertEquals(item.thumbnailUrl, mapped.thumbnailUrl)
        Assert.assertEquals(item.detailsUrl, mapped.detailsUrl)
    }
}