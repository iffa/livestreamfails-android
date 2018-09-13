package digital.sogood.livestreamfails.mobile.ui.fail

import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiFragment
import digital.sogood.livestreamfails.presentation.cases.fail.FailContract
import digital.sogood.livestreamfails.presentation.cases.fail.FailPresenter
import digital.sogood.livestreamfails.presentation.model.FailView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFragment : DaggerTiFragment<FailPresenter, FailContract>(), FailContract {
    @Inject
    lateinit var failPresenter: FailPresenter

    override fun providePresenter(): FailPresenter = failPresenter

    override fun showProgress() {
        TODO("Not implemented")
    }

    override fun hideProgress() {
        TODO("Not implemented")
    }

    override fun showFails(fails: List<FailView>) {
        TODO("Not implemented")
    }

    override fun clearFails() {
        TODO("Not implemented")
    }

    override fun hideFails() {
        TODO("Not implemented")
    }

    override fun showErrorState() {
        TODO("Not implemented")
    }

    override fun hideErrorState() {
        TODO("Not implemented")
    }

    override fun showEmptyState() {
        TODO("Not implemented")
    }

    override fun hideEmptyState() {
        TODO("Not implemented")
    }

    override fun showNoMoreResultsState() {
        TODO("Not implemented")
    }
}
