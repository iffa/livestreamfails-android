package digital.sogood.livestreamfails.data.test.factory

import digital.sogood.livestreamfails.data.model.DetailsEntity
import digital.sogood.livestreamfails.domain.model.Details

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFactory {
    companion object {
        fun makeDetails(): Details {
            return Details(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomBoolean(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }

        fun makeDetailsEntity(): DetailsEntity {
            return DetailsEntity(DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomUuid(), DataFactory.randomInt(), DataFactory.randomBoolean(), DataFactory.randomUuid(), DataFactory.randomUuid())
        }
    }
}