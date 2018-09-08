package digital.sogood.livestreamfails.domain.test.factory

import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for [Streamer] related instances
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerFactory {
    companion object Factory {
        fun makeStreamerList(count: Int): List<Streamer> {
            val fails = mutableListOf<Streamer>()
            repeat(count) {
                fails.add(makeStreamer())
            }
            return fails
        }

        fun makeStreamer(): Streamer {
            return Streamer(randomUuid(), randomInt(), randomUuid())
        }
    }
}