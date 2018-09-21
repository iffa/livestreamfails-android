package digital.sogood.livestreamfails.mobile.ui.fail

import android.content.SharedPreferences
import android.os.Bundle
import android.os.Parcelable
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ajalt.timberkt.Timber
import com.google.android.material.snackbar.Snackbar
import com.mikepenz.itemanimators.AlphaCrossFadeAnimator
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.mapper.FailViewModelMapper
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiActivity
import digital.sogood.livestreamfails.mobile.ui.base.config
import digital.sogood.livestreamfails.mobile.ui.base.list.EndlessScrollListener
import digital.sogood.livestreamfails.mobile.ui.base.view.FloatingTextButton
import digital.sogood.livestreamfails.mobile.ui.details.DetailsAltActivity
import digital.sogood.livestreamfails.mobile.ui.menu.MenuDialogFragment
import digital.sogood.livestreamfails.presentation.cases.fail.FailContract
import digital.sogood.livestreamfails.presentation.cases.fail.FailPresenter
import digital.sogood.livestreamfails.presentation.model.FailView
import kotlinx.android.synthetic.main.activity_fail.*
import javax.inject.Inject

class FailActivity : DaggerTiActivity<FailPresenter, FailContract>(), FailContract, SharedPreferences.OnSharedPreferenceChangeListener {
    @Inject
    lateinit var failPresenter: FailPresenter

    @Inject
    lateinit var mapper: FailViewModelMapper

    private lateinit var preferences: SharedPreferences

    private lateinit var adapter: FailAdapter

    private var listState: Parcelable? = null

    companion object {
        private const val LIST_STATE_KEY = "recycler_state"
        private const val LIST_CONTENTS_KEY = "recycler_contents"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fail)

        setSupportActionBar(toolbar)

        preferences = PreferenceManager.getDefaultSharedPreferences(this)
        presenter.onNsfwChanged(preferences.getBoolean(MenuDialogFragment.PREF_SHOW_NSFW, false))

        setupRecyclerView()

        scrollToTopFab.setOnClickListener {
            recyclerView.scrollToPosition(0)
            (it as FloatingTextButton).animateOut()
        }
    }

    override fun providePresenter(): FailPresenter = failPresenter

    override fun showProgress() {
        progressBar.show()
    }

    override fun showLoadingMoreProgress() {
        loadMoreProgressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
        loadMoreProgressBar.hide()
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
        Toast.makeText(this, R.string.loading_error, Toast.LENGTH_SHORT).show()
        // TODO
    }

    override fun hideErrorState() {
        // TODO
    }

    override fun showEmptyState() {
        Toast.makeText(this, R.string.empty_list, Toast.LENGTH_SHORT).show()
        emptyListText.visibility = View.VISIBLE
    }

    override fun hideEmptyState() {
        emptyListText.visibility = View.GONE
    }

    override fun showNoMoreResultsState() {
        Snackbar.make(rootLayout, R.string.no_more_results, Snackbar.LENGTH_LONG)
                .config(this).show()
    }


    private fun setupRecyclerView() {
        adapter = FailAdapter(
                selectedTimeFrame = presenter.timeFrame,
                selectedOrder = presenter.order,
                timeframeListener = {
                    Timber.d { "${it.name} chip selected" }
                    presenter.onTimeFrameChanged(it)
                },
                orderListener = {
                    Timber.d { "${it.name} chip selected" }
                    presenter.onOrderChanged(it)
                },
                itemClickListener = { item -> showDetails(item) })

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.itemAnimator = AlphaCrossFadeAnimator()
        recyclerView.addOnScrollListener(EndlessScrollListener {
            presenter.onScrollToEnd()
        })
    }

    private fun showDetails(item: FailViewModel) {
        Timber.d { "${item.postId} fail clicked" }

        val startIntent = DetailsAltActivity.getStartIntent(this, item)

        startActivity(startIntent)
    }

    override fun onPause() {
        super.onPause()

        preferences.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onResume() {
        super.onResume()

        preferences.registerOnSharedPreferenceChangeListener(this)

        listState?.let {
            Timber.v { "Restoring RecyclerView state" }
            recyclerView.layoutManager?.onRestoreInstanceState(it)
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelable(LIST_STATE_KEY, recyclerView.layoutManager?.onSaveInstanceState())
        outState?.putParcelableArrayList(LIST_CONTENTS_KEY, ArrayList(adapter.getItems()))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_menu -> MenuDialogFragment()
                    .show(supportFragmentManager, MenuDialogFragment::class.java.name)
        }
        return true
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            MenuDialogFragment.PREF_SHOW_NSFW -> {
                val showNsfw = preferences.getBoolean(MenuDialogFragment.PREF_SHOW_NSFW, false)
                Timber.d { "Show NSFW flag changed, new value: $showNsfw" }
                presenter.onNsfwChanged(showNsfw)
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if (savedInstanceState != null) {
            Timber.v { "Restoring view state" }

            listState = savedInstanceState.getParcelable(LIST_STATE_KEY)
            savedInstanceState.getParcelableArrayList<FailViewModel>(LIST_CONTENTS_KEY)?.let {
                adapter.submitList(it.toList())
            }
        }
    }
}
