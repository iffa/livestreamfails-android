package digital.sogood.livestreamfails.presentation.test.factory

import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.presentation.model.FailView
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomLong
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomUuid


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {
        fun makeFailView(): FailView {
            return FailView(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomLong())
        }

        fun makeFail(): Fail {
            return Fail(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomLong())
        }

        fun makeFailList(count: Int): List<Fail> {
            val entities = mutableListOf<Fail>()
            repeat(count) {
                entities.add(makeFail())
            }
            return entities
        }

        fun makeFailViewList(count: Int): List<FailView> {
            val entities = mutableListOf<FailView>()
            repeat(count) {
                entities.add(makeFailView())
            }
            return entities
        }
    }
}