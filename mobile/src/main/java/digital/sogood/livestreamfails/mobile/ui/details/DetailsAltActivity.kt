package digital.sogood.livestreamfails.mobile.ui.details

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.ajalt.timberkt.Timber
import com.google.android.exoplayer2.C
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.mapper.DetailsViewModelMapper
import digital.sogood.livestreamfails.mobile.model.DetailsViewModel
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.ui.base.DaggerTiActivity
import digital.sogood.livestreamfails.presentation.cases.details.DetailsContract
import digital.sogood.livestreamfails.presentation.cases.details.DetailsPresenter
import digital.sogood.livestreamfails.presentation.model.DetailsView
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder
import xyz.klinker.android.drag_dismiss.DragDismissIntentBuilder.DragElasticity.XXLARGE
import xyz.klinker.android.drag_dismiss.delegate.AbstractDragDismissDelegate
import xyz.klinker.android.drag_dismiss.delegate.DragDismissDelegate
import javax.inject.Inject


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsAltActivity : DaggerTiActivity<DetailsPresenter, DetailsContract>(), DetailsContract, DragDismissDelegate.Callback {
    @Inject
    lateinit var detailsPresenter: DetailsPresenter

    @Inject
    lateinit var mapper: DetailsViewModelMapper

    private var detailsItem: DetailsViewModel? = null

    private var mediaSource: MediaSource? = null
    private var trackSelector: TrackSelector? = null
    private var player: SimpleExoPlayer? = null

    private var startAutoPlay: Boolean? = null
    private var startWindow: Int? = null
    private var startPosition: Long? = null

    private lateinit var dragDismissDelegate: AbstractDragDismissDelegate
    private lateinit var playerView: PlayerView

    companion object {
        private const val EXTRA_FAIL = "fail_item"

        private const val KEY_AUTO_PLAY = "play_when_ready"
        private const val KEY_WINDOW = "current_window"
        private const val KEY_POSITION = "playback_position"
        private const val KEY_DETAILS_ITEM = "details_item"

        @JvmStatic
        fun getStartIntent(context: Context, fail: FailViewModel): Intent {
            val intent = Intent(context, DetailsAltActivity::class.java)
            intent.putExtra(EXTRA_FAIL, fail)

            DragDismissIntentBuilder(context)
                    .setShowToolbar(false)
                    .setDrawUnderStatusBar(false)
                    .setDragElasticity(XXLARGE)
                    .build(intent)

            return intent
        }
    }

    override fun providePresenter(): DetailsPresenter {
        val fail = intent.getParcelableExtra<FailViewModel>(EXTRA_FAIL)
                ?: throw NullPointerException("No fail passed as extra to DetailsActivity")
        detailsPresenter.postId = fail.postId
        return detailsPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        dragDismissDelegate = DragDismissDelegate(this, this)
        dragDismissDelegate.onCreate(savedInstanceState)
    }

    override fun onCreateContent(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater?.inflate(R.layout.activity_details_alt, parent, false)!!

        playerView = view.findViewById(R.id.playerView)

        if (savedInstanceState != null) {
            startAutoPlay = savedInstanceState.getBoolean(KEY_AUTO_PLAY)
            startWindow = savedInstanceState.getInt(KEY_WINDOW)
            startPosition = savedInstanceState.getLong(KEY_POSITION)
        } else {
            clearStartPosition()
        }

        return view
    }

    override fun showProgress() {
        dragDismissDelegate.showProgressBar()
    }

    override fun hideProgress() {
        dragDismissDelegate.hideProgressBar()
    }

    override fun showVideo(details: DetailsView) {
        Timber.d { "Received video url" }

        detailsItem = mapper.mapToViewModel(details)
        detailsItem?.let { initializePlayer(it.videoUrl) }
    }

    override fun hideVideo() {
    }

    override fun showErrorState() {
    }

    override fun hideErrorState() {
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        updateStartPosition()

        outState?.putParcelable(KEY_DETAILS_ITEM, detailsItem)
        startAutoPlay?.let { outState?.putBoolean(KEY_AUTO_PLAY, it) }
        startWindow?.let { outState?.putInt(KEY_WINDOW, it) }
        startPosition?.let { outState?.putLong(KEY_POSITION, it) }

        super.onSaveInstanceState(outState)
    }

    override fun onStart() {
        super.onStart()

        if (Build.VERSION.SDK_INT > 23) {
            detailsItem?.let { initializePlayer(it.videoUrl) }
        }
    }

    override fun onResume() {
        super.onResume()

        if (Build.VERSION.SDK_INT <= 23 || player == null) {
            detailsItem?.let { initializePlayer(it.videoUrl) }
        }
    }

    override fun onPause() {
        super.onPause()

        if (Build.VERSION.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()

        if (Build.VERSION.SDK_INT > 23) {
            releasePlayer()
        }
    }

    private fun initializePlayer(videoUrl: String) {
        Timber.d { "Initializing player" }

        if (player == null) {
            val bandwidthMeter = DefaultBandwidthMeter()
            val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
            val dataSourceFactory = DefaultDataSourceFactory(this, Util.getUserAgent(this, "livestreamfails-android"))
            trackSelector = DefaultTrackSelector(trackSelectionFactory)
            mediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                    .createMediaSource(Uri.parse(videoUrl))

            player = ExoPlayerFactory.newSimpleInstance(this, trackSelector)
            player?.repeatMode = Player.REPEAT_MODE_ONE
            player?.playWhenReady = startAutoPlay!!
            playerView.player = player
            playerView.setPlaybackPreparer {
                initializePlayer(videoUrl)
            }
        }
        val haveStartPosition = startWindow != C.INDEX_UNSET
        if (haveStartPosition) {
            player?.seekTo(startWindow!!, startPosition!!)
        }

        player?.prepare(mediaSource, !haveStartPosition, false)
    }

    private fun releasePlayer() {
        Timber.d { "Releasing player" }

        player?.let {
            updateStartPosition()
            it.release()
            player = null
            mediaSource = null
            trackSelector = null
        }
    }

    private fun updateStartPosition() {
        player?.let {
            startAutoPlay = it.playWhenReady
            startWindow = it.currentWindowIndex
            startPosition = Math.max(0, it.contentPosition)
        }
    }

    private fun clearStartPosition() {
        startAutoPlay = true
        startWindow = C.INDEX_UNSET
        startPosition = C.TIME_UNSET
    }
}
