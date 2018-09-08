package digital.sogood.livestreamfails.domain.test.factory

import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for [Fail] related instances
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {
        fun makeFailList(count: Int): List<Fail> {
            val fails = mutableListOf<Fail>()
            repeat(count) {
                fails.add(makeFail())
            }
            return fails
        }

        fun makeFail(): Fail {
            return Fail(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid())
        }
    }
}