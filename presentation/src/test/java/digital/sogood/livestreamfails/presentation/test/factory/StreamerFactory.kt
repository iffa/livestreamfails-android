package digital.sogood.livestreamfails.presentation.test.factory

import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.presentation.model.StreamerView
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerFactory {
    companion object Factory {

        fun makeStreamerView(): StreamerView {
            return StreamerView(randomUuid(), randomInt(), randomUuid())
        }

        fun makeStreamer(): Streamer {
            return Streamer(randomUuid(), randomInt(), randomUuid())
        }

        fun makeStreamerViewList(count: Int): List<StreamerView> {
            val entities = mutableListOf<StreamerView>()
            repeat(count) {
                entities.add(makeStreamerView())
            }
            return entities
        }

        fun makeStreamerList(count: Int): List<Streamer> {
            val entities = mutableListOf<Streamer>()
            repeat(count) {
                entities.add(makeStreamer())
            }
            return entities
        }
    }
}