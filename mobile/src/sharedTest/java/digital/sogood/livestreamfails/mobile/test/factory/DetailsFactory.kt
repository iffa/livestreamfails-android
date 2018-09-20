package digital.sogood.livestreamfails.mobile.test.factory

import digital.sogood.livestreamfails.mobile.model.DetailsViewModel
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.presentation.model.DetailsView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFactory {
    companion object Factory {
        fun makeDetailsView(): DetailsView {
            return DetailsView(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }

        fun makeDetailsViewModel(): DetailsViewModel {
            return DetailsViewModel(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }
    }
}