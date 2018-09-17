package digital.sogood.livestreamfails.mobile.ui.fail

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.mapper.FailViewModelMapper
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiFragment
import digital.sogood.livestreamfails.mobile.ui.base.list.EndlessScrollListener
import digital.sogood.livestreamfails.mobile.ui.details.DetailsActivity
import digital.sogood.livestreamfails.mobile.ui.main.MainActivity
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

    private lateinit var adapter: FailAdapter

    private var listState: Parcelable? = null

    companion object {
        private const val LIST_STATE_KEY = "recycler_state"
        private const val LIST_CONTENTS_KEY = "recycler_contents"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.fragment_fail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        (activity as MainActivity).getScrollToTopButton()?.let {
            it.setOnClickListener { _ ->
                Timber.d { "Scroll to top button clicked" }
                recyclerView.smoothScrollToPosition(0)
                it.animateOut()
            }
        }
    }

    override fun providePresenter(): FailPresenter = failPresenter

    override fun showProgress() {
        Toast.makeText(context, R.string.loading, Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
    }

    override fun showFails(fails: List<FailView>) {
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
        Toast.makeText(context, R.string.loading_error, Toast.LENGTH_SHORT).show()
        // TODO
    }

    override fun hideErrorState() {
        // TODO
    }

    override fun showEmptyState() {
        Toast.makeText(context, R.string.empty_list, Toast.LENGTH_SHORT).show()
        emptyListText.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyListText.visibility = View.GONE
    }

    override fun showNoMoreResultsState() {
        Toast.makeText(context, R.string.no_more_results, Toast.LENGTH_LONG).show()
        // TODO
    }

    private fun setupRecyclerView() {
        adapter = FailAdapter(
                selectedTimeFrame = presenter.getCurrentTimeFrame(),
                selectedOrder = presenter.getCurrentOrder(),
                timeframeListener = {
                    Timber.d { "${it.name} chip selected" }
                    presenter.onTimeFrameChanged(it)
                },
                orderListener = {
                    Timber.d { "${it.name} chip selected" }
                    presenter.onOrderChanged(it)
                },
                itemClickListener = { item, thumbnail -> showDetails(item, thumbnail) })

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(EndlessScrollListener {
            Timber.v { "Scroll listener -> end of scroll, asking presenter for more items" }
            presenter.retrieveFails()
        })
    }

    private fun showDetails(item: FailViewModel, thumbnail: ImageView) {
        Timber.d { "${item.postId} fail clicked" }

        val startIntent = DetailsActivity.getStartIntent(requireActivity(), item,
                ViewCompat.getTransitionName(thumbnail))

        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                requireActivity(),
                Pair(thumbnail, ViewCompat.getTransitionName(thumbnail))
        )

        startActivity(startIntent, options.toBundle())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable(LIST_STATE_KEY, recyclerView.layoutManager?.onSaveInstanceState())
        outState.putParcelableArrayList(LIST_CONTENTS_KEY, ArrayList(adapter.getItems()))
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        if (savedInstanceState != null) {
            Timber.v { "Restoring view state" }

            listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
            savedInstanceState.getParcelableArrayList<FailViewModel>(LIST_CONTENTS_KEY)?.let {
                adapter.submitList(it.toList())
            }
        }
    }

    override fun onResume() {
        super.onResume()

        listState?.let {
            Timber.v { "Restoring RecyclerView state" }
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }
}
