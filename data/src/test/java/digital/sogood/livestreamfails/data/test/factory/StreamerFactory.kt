package digital.sogood.livestreamfails.data.test.factory

import digital.sogood.livestreamfails.data.model.StreamerEntity
import digital.sogood.livestreamfails.domain.model.Streamer

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerFactory {
    companion object Factory {

        fun makeStreamerEntity(): StreamerEntity {
            return StreamerEntity(DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomUuid())
        }

        fun makeStreamer(): Streamer {
            return Streamer(DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomUuid())
        }

        fun makeStreamerEntityList(count: Int): List<StreamerEntity> {
            val entities = mutableListOf<StreamerEntity>()
            repeat(count) {
                entities.add(makeStreamerEntity())
            }
            return entities
        }

        fun makeStreamerList(count: Int): List<Streamer> {
            val items = mutableListOf<Streamer>()
            repeat(count) {
                items.add(makeStreamer())
            }
            return items
        }
    }
}