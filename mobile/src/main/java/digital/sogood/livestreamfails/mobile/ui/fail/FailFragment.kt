package digital.sogood.livestreamfails.mobile.ui.fail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.mobile.mapper.FailViewModelMapper
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiFragment
import digital.sogood.livestreamfails.mobile.ui.base.list.EndlessScrollListener
import digital.sogood.livestreamfails.presentation.cases.fail.FailContract
import digital.sogood.livestreamfails.presentation.cases.fail.FailPresenter
import digital.sogood.livestreamfails.presentation.model.FailView
import kotlinx.android.synthetic.main.fragment_fail.*
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailFragment : DaggerTiFragment<FailPresenter, FailContract>(), FailContract {
    @Inject
    lateinit var failPresenter: FailPresenter

    @Inject
    lateinit var mapper: FailViewModelMapper

    @Inject
    lateinit var adapter: FailAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_fail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
    }

    override fun providePresenter(): FailPresenter = failPresenter

    override fun showProgress() {
        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
        //progressBar.show()
    }

    override fun hideProgress() {
        //progressBar.hide()
    }

    override fun showFails(fails: List<FailView>) {
        Toast.makeText(context, "Showing fails", Toast.LENGTH_SHORT).show()
        recyclerView.visibility = View.VISIBLE
        adapter.addItems(fails.map { mapper.mapToViewModel(it) })
    }

    override fun clearFails() {
        adapter.clear()
    }

    override fun hideFails() {
        recyclerView.visibility = View.GONE
    }

    override fun showErrorState() {
        Toast.makeText(context, "Error state", Toast.LENGTH_SHORT).show()
        // TODO
    }

    override fun hideErrorState() {
        // TODO
    }

    override fun showEmptyState() {
        Toast.makeText(context, "Empty state", Toast.LENGTH_SHORT).show()
        emptyListText.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyListText.visibility = View.GONE
    }

    override fun showNoMoreResultsState() {
        Toast.makeText(context, "No more results", Toast.LENGTH_LONG).show()
        // TODO
    }

    private fun setupRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(EndlessScrollListener {
            Timber.v { "Scroll listener -> end of scroll, asking presenter for more items" }
            presenter.retrieveFails(TimeFrame.DAY, Order.HOT, false)
        })
    }
}
