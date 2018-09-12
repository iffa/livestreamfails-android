package digital.sogood.livestreamfails.mobile.mapper

import digital.sogood.livestreamfails.mobile.test.factory.StreamerFactory
import org.junit.Assert
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerViewModelMapperTest {
    private val mapper = StreamerViewModelMapper()

    @Test
    fun mapToViewTest() {
        val item = StreamerFactory.makeStreamerView()
        val mapped = mapper.mapToViewModel(item)

        Assert.assertEquals(item.name, mapped.name)
        Assert.assertEquals(item.fails, mapped.fails)
        Assert.assertEquals(item.imageUrl, mapped.imageUrl)
    }
}