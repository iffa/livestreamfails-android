package digital.sogood.livestreamfails.presentation.test

import digital.sogood.livestreamfails.domain.interactor.cases.GetDetails
import digital.sogood.livestreamfails.presentation.cases.details.DetailsPresenter
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class TestDetailsPresenter(useCase: GetDetails, mapper: DetailsViewMapper)
    : DetailsPresenter(useCase, mapper) {
    override fun firstLoad() {
        // Don't first load in tests
    }
}