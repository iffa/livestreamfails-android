package digital.sogood.livestreamfails.remote.test.factory

import digital.sogood.livestreamfails.remote.model.FailModel
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {

        fun makeFailModel(): FailModel {
            return FailModel(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }

        fun makeFailModelList(count: Int): List<FailModel> {
            val entities = mutableListOf<FailModel>()
            repeat(count) {
                entities.add(makeFailModel())
            }
            return entities
        }
    }
}