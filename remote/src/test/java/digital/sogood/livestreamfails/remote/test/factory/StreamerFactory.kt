package digital.sogood.livestreamfails.remote.test.factory

import digital.sogood.livestreamfails.remote.model.StreamerModel
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerFactory {
    companion object Factory {

        fun makeStreamerModel(): StreamerModel {
            return StreamerModel(randomUuid(), randomInt(), randomUuid())
        }

        fun makeStreamerModelList(count: Int): List<StreamerModel> {
            val entities = mutableListOf<StreamerModel>()
            repeat(count) {
                entities.add(makeStreamerModel())
            }
            return entities
        }
    }
}