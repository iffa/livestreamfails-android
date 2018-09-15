package digital.sogood.livestreamfails.mobile.test.factory

import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.presentation.model.FailView


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {
        fun makeFailView(): FailView {
            return FailView(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }

        fun makeFailViewModel(): FailViewModel {
            return FailViewModel(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), randomUuid(), randomUuid())
        }

        fun makeFailViewModelList(count: Int): List<FailViewModel> {
            val entities = mutableListOf<FailViewModel>()
            repeat(count) {
                entities.add(makeFailViewModel())
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