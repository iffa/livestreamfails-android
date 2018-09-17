package digital.sogood.livestreamfails.domain.model

import org.junit.Test
import kotlin.test.assertEquals

/**
 * This is ridiculous, but gotta have that coverage.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class ModelTest {
    @Test
    fun detailsTest() {
        val item = Details("Title", "Streamer", "Game", 0, false, "video", "source")

        assertEquals("Title", item.title)
        assertEquals("Streamer", item.streamer)
        assertEquals("Game", item.game)
        assertEquals(0, item.points)
        assertEquals(false, item.nsfw)
        assertEquals("video", item.videoUrl)
        assertEquals("source", item.sourceUrl)
    }

    @Test
    fun failTest() {
        val item = Fail("Title", "Streamer", "Game", 0, false, "thumbnail", 0)

        assertEquals("Title", item.title)
        assertEquals("Streamer", item.streamer)
        assertEquals("Game", item.game)
        assertEquals(0, item.points)
        assertEquals(false, item.nsfw)
        assertEquals("thumbnail", item.thumbnailUrl)
        assertEquals(0, item.postId)
    }

    @Test
    fun gameTest() {
        val item = Game("Name", 0, "image")

        assertEquals("Name", item.name)
        assertEquals(0, item.fails)
        assertEquals("image", item.imageUrl)
    }

    @Test
    fun streamerTest() {
        val item = Streamer("Name", 0, "image")

        assertEquals("Name", item.name)
        assertEquals(0, item.fails)
        assertEquals("image", item.imageUrl)
    }
}