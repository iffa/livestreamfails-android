package digital.sogood.livestreamfails.mobile.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.mapper.DetailsViewModelMapper
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiFragment
import digital.sogood.livestreamfails.presentation.cases.details.DetailsContract
import digital.sogood.livestreamfails.presentation.cases.details.DetailsPresenter
import digital.sogood.livestreamfails.presentation.model.DetailsView
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsFragment : DaggerTiFragment<DetailsPresenter, DetailsContract>(), DetailsContract {
    companion object {
        private const val POST_ID_KEY = "post_id"

        fun newInstance(postId: Long): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundleOf(Pair(POST_ID_KEY, postId))
            return fragment
        }
    }

    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    @Inject
    lateinit var mapper: DetailsViewModelMapper

    override fun providePresenter(): DetailsPresenter {
        return detailsPresenter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.activity_details, container, false)
    }

    override fun showProgress() {
    }

    override fun hideProgress() {
    }

    override fun showVideo(details: DetailsView) {
        titleText.text = details.title

        val hasSubtitleData = !details.streamer.isNullOrEmpty() || !details.game.isNullOrEmpty()
        when (hasSubtitleData) {
            true -> subtitleText.text = getString(R.string.tv_fail_subtitle, details.streamer, details.game)
            false -> subtitleText.text = getString(R.string.tv_fail_subtitle_none)
        }

        pointsText.text = getString(R.string.tv_fail_points, details.points)

        nsfwText.visibility = when (details.nsfw) {
            true -> View.VISIBLE
            false -> View.GONE
        }
    }

    override fun hideVideo() {
    }

    override fun showErrorState() {
    }

    override fun hideErrorState() {
    }
}