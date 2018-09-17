package digital.sogood.livestreamfails.data.test.factory

import digital.sogood.livestreamfails.data.model.FailEntity
import digital.sogood.livestreamfails.data.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.data.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.data.test.factory.DataFactory.Factory.randomLong
import digital.sogood.livestreamfails.data.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.domain.model.Fail

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {

        fun makeFailEntity(): FailEntity {
            return FailEntity(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomLong())
        }

        fun makeFail(): Fail {
            return Fail(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomLong())
        }

        fun makeFailEntityList(count: Int): List<FailEntity> {
            val entities = mutableListOf<FailEntity>()
            repeat(count) {
                entities.add(makeFailEntity())
            }
            return entities
        }

        fun makeFailList(count: Int): List<Fail> {
            val items = mutableListOf<Fail>()
            repeat(count) {
                items.add(makeFail())
            }
            return items
        }

    }
}