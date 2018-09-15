package digital.sogood.livestreamfails.mobile.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.transition.TransitionManager
import com.github.ajalt.timberkt.Timber
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.mapper.DetailsViewModelMapper
import digital.sogood.livestreamfails.mobile.model.DetailsViewModel
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiActivity
import digital.sogood.livestreamfails.presentation.cases.details.DetailsContract
import digital.sogood.livestreamfails.presentation.cases.details.DetailsPresenter
import digital.sogood.livestreamfails.presentation.model.DetailsView
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsActivity : DaggerTiActivity<DetailsPresenter, DetailsContract>(), DetailsContract {
    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    @Inject
    lateinit var mapper: DetailsViewModelMapper

    private var detailsItem: DetailsViewModel? = null

    private var player: SimpleExoPlayer? = null
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

    companion object {
        private const val POST_ID_KEY = "post_id"
        private const val PLAY_WHEN_READY_KEY = "play_when_ready"
        private const val CURRENT_WINDOW_KEY = "current_window"
        private const val PLAYBACK_POSITION_KEY = "playback_position"
        private const val DETAILS_ITEM = "details_item"

        @JvmStatic
        fun getStartIntent(context: Context, postId: Long): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(POST_ID_KEY, postId)
            return intent
        }
    }

    override fun providePresenter(): DetailsPresenter {
        detailsPresenter.retrieveDetails(intent.getLongExtra(POST_ID_KEY, -1))
        return detailsPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun showDetails(details: DetailsView) {
        Timber.d { "Received details item from presenter" }

        detailsItem = mapper.mapToViewModel(details)

        showDetails(detailsItem!!)
    }

    private fun showDetails(details: DetailsViewModel) {
        TransitionManager.beginDelayedTransition(constraintLayout)
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

        updateToolbar(details.title)

        prepareVideoPlayer(details.videoUrl)
    }

    private fun updateToolbar(title: String) {
        supportActionBar?.subtitle = title
    }

    override fun hideDetails() {
        // TODO
    }

    override fun showErrorState() {
        // TODO
    }

    override fun hideErrorState() {
        // TODO
    }

    private fun prepareVideoPlayer(videoUrl: String) {
        Timber.d { "Preparing video player" }

        val bandwidthMeter = DefaultBandwidthMeter()
        val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        val trackSelector = DefaultTrackSelector(trackSelectionFactory)

        val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "livestream-fails"))
        val videoSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl))

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
        player?.addListener(object : Player.DefaultEventListener() {
            override fun onPlayerError(error: ExoPlaybackException?) {
                Timber.e(error) { "Error playing clip" }
            }

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                when (playbackState) {
                    Player.STATE_READY -> {
                        playerView.visibility = View.VISIBLE
                        thumbnailImage.visibility = View.INVISIBLE
                    }
                }
            }

        })

        player?.prepare(videoSource)
        player?.playWhenReady = playWhenReady
        val haveStartPosition = currentWindow != C.INDEX_UNSET
        if (haveStartPosition) {
            player?.seekTo(currentWindow, playbackPosition)
        }

        playerView.player = player
    }

    override fun onStop() {
        super.onStop()

        player?.playWhenReady = false
    }

    override fun onStart() {
        super.onStart()

        player?.playWhenReady = true
    }

    override fun onDestroy() {
        super.onDestroy()

        player?.release()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        player?.let {
            outState?.putBoolean(PLAY_WHEN_READY_KEY, it.playWhenReady)
            outState?.putInt(CURRENT_WINDOW_KEY, it.currentWindowIndex)
            outState?.putLong(PLAYBACK_POSITION_KEY, it.currentPosition)
            outState?.putParcelable(DETAILS_ITEM, detailsItem)
        }

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        playWhenReady = savedInstanceState?.getBoolean(PLAY_WHEN_READY_KEY) ?: false
        currentWindow = savedInstanceState?.getInt(CURRENT_WINDOW_KEY) ?: 0
        playbackPosition = savedInstanceState?.getLong(PLAYBACK_POSITION_KEY) ?: 0
        detailsItem = savedInstanceState?.getParcelable(DETAILS_ITEM)

        detailsItem?.let {
            showDetails(it)
        }
    }
}
