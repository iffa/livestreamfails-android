package digital.sogood.livestreamfails.mobile.test.factory

import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.THUMBNAIL_PLACEHOLDER_URL
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomBoolean
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomLong
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.presentation.model.FailView


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFactory {
    companion object Factory {
        fun makeFailView(): FailView {
            return FailView(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), THUMBNAIL_PLACEHOLDER_URL, randomLong())
        }

        fun makeFailViewModel(): FailViewModel {
            return FailViewModel(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), THUMBNAIL_PLACEHOLDER_URL, randomLong())
        }

        fun makeFail(): Fail {
            return Fail(randomUuid(), randomUuid(), randomUuid(), randomInt(), randomBoolean(), THUMBNAIL_PLACEHOLDER_URL, randomLong())
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

        fun makeFailList(count: Int): List<Fail> {
            val entities = mutableListOf<Fail>()
            repeat(count) {
                entities.add(makeFail())
            }
            return entities
        }
    }
}