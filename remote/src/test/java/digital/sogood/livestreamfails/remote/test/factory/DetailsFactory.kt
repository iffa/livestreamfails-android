package digital.sogood.livestreamfails.remote.test.factory

import digital.sogood.livestreamfails.remote.model.DetailsModel
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.remote.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFactory {
    companion object Factory {
        fun makeDetailsModel(): DetailsModel {
            return DetailsModel(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }
    }
}