package digital.sogood.livestreamfails.mobile.mapper

import digital.sogood.livestreamfails.mobile.test.factory.GameFactory
import org.junit.Assert
import org.junit.Test

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GameViewModelMapperTest {
    private val mapper = GameViewModelMapper()

    @Test
    fun mapToViewTest() {
        val item = GameFactory.makeGameView()
        val mapped = mapper.mapToViewModel(item)

        Assert.assertEquals(item.name, mapped.name)
        Assert.assertEquals(item.fails, mapped.fails)
        Assert.assertEquals(item.imageUrl, mapped.imageUrl)
    }
}