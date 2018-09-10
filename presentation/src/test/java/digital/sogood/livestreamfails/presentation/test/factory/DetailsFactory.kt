package digital.sogood.livestreamfails.presentation.test.factory

import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.presentation.model.DetailsView
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.presentation.test.factory.DataFactory.Factory.randomUuid

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFactory {
    companion object Factory {
        fun makeDetailsView(): DetailsView {
            return DetailsView(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }

        fun makeDetails(): Details {
            return Details(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }
    }
}