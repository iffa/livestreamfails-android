package digital.sogood.livestreamfails.mobile.test.factory

import digital.sogood.livestreamfails.mobile.model.StreamerViewModel
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomInt
import digital.sogood.livestreamfails.mobile.test.factory.DataFactory.Factory.randomUuid
import digital.sogood.livestreamfails.presentation.model.StreamerView

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerFactory {
    companion object Factory {

        fun makeStreamerView(): StreamerView {
            return StreamerView(randomUuid(), randomInt(), randomUuid())
        }

        fun makeStreamerViewModel(): StreamerViewModel {
            return StreamerViewModel(randomUuid(), randomInt(), randomUuid())
        }

        fun makeStreamerViewList(count: Int): List<StreamerView> {
            val entities = mutableListOf<StreamerView>()
            repeat(count) {
                entities.add(makeStreamerView())
            }
            return entities
        }

        fun makeStreamerViewModelList(count: Int): List<StreamerViewModel> {
            val entities = mutableListOf<StreamerViewModel>()
            repeat(count) {
                entities.add(makeStreamerViewModel())
            }
            return entities
        }
    }
}