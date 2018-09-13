package digital.sogood.livestreamfails.presentation.test

import digital.sogood.livestreamfails.domain.interactor.cases.GetFails
import digital.sogood.livestreamfails.presentation.cases.fail.FailPresenter
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class TestFailPresenter(useCase: GetFails,
                        mapper: FailViewMapper) : FailPresenter(useCase, mapper) {
    override fun firstLoad() {
        // Don't first load in tests
    }
}