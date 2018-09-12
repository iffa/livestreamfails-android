package digital.sogood.livestreamfails.mobile.ui.streamer

import digital.sogood.livestreamfails.mobile.mapper.StreamerViewModelMapper
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiActivity
import digital.sogood.livestreamfails.presentation.cases.streamer.StreamerContract
import digital.sogood.livestreamfails.presentation.cases.streamer.StreamerPresenter
import digital.sogood.livestreamfails.presentation.model.StreamerView
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerActivity : DaggerTiActivity<StreamerPresenter, StreamerContract>(), StreamerContract {
    @Inject
    lateinit var mapper: StreamerViewModelMapper

    @Inject
    lateinit var streamerPresenter: StreamerPresenter

    override fun providePresenter(): StreamerPresenter {
        return streamerPresenter
    }

    override fun showProgress() {
        TODO("Not implemented")
    }

    override fun hideProgress() {
        TODO("Not implemented")
    }

    override fun showStreamers(streamers: List<StreamerView>) {
        TODO("Not implemented")
    }

    override fun hideStreamers() {
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