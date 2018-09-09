package digital.sogood.livestreamfails.domain.test.factory

import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFactory {
    companion object {
        fun makeDetails(): Details {
            return Details(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }
    }
}