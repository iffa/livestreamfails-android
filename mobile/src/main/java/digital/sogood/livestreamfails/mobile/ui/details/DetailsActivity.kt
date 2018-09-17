package digital.sogood.livestreamfails.mobile.ui.details

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
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
import digital.sogood.livestreamfails.mobile.model.FailViewModel
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
        private const val EXTRA_FAIL = "fail_item"
        private const val EXTRA_TRANSITION_THUMBNAIL = "transition_thumbnail"

        private const val PLAY_WHEN_READY_KEY = "play_when_ready"
        private const val CURRENT_WINDOW_KEY = "current_window"
        private const val PLAYBACK_POSITION_KEY = "playback_position"
        private const val DETAILS_ITEM = "details_item"

        @JvmStatic
        fun getStartIntent(context: Context, fail: FailViewModel, thumbnailTransition: String?): Intent {
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra(EXTRA_FAIL, fail)
            intent.putExtra(EXTRA_TRANSITION_THUMBNAIL, thumbnailTransition)
            return intent
        }
    }

    override fun providePresenter(): DetailsPresenter {
        val fail = intent.getParcelableExtra<FailViewModel>(EXTRA_FAIL)
                ?: throw NullPointerException("No fail passed as extra to DetailsActivity")
        detailsPresenter.retrieveDetails(fail.postId)
        return detailsPresenter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        supportPostponeEnterTransition()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        thumbnailImage.transitionName = intent?.extras?.getString(EXTRA_TRANSITION_THUMBNAIL)

        val fail = intent?.extras?.getParcelable<FailViewModel>(EXTRA_FAIL)
                ?: throw NullPointerException("No fail passed as extra to DetailsActivity")

        Glide.with(this)
                .load(fail.thumbnailUrl)
                .apply(RequestOptions()
                        .dontTransform()
                        .onlyRetrieveFromCache(true)
                ).listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        supportStartPostponedEnterTransition()
                        return false
                    }
                })
                .into(thumbnailImage)

        titleText.text = fail.title

        val hasSubtitleData = !fail.streamer.isNullOrEmpty() || !fail.game.isNullOrEmpty()
        when (hasSubtitleData) {
            true -> subtitleText.text = getString(R.string.tv_fail_subtitle, fail.streamer, fail.game)
            false -> subtitleText.text = getString(R.string.tv_fail_subtitle_none)
        }

        pointsText.text = getString(R.string.tv_fail_points, fail.points)

        nsfwText.visibility = when (fail.nsfw) {
            true -> View.VISIBLE
            false -> View.GONE
        }

        updateToolbar(fail.title)
    }

    override fun showProgress() {
        progressBar.show()
    }

    override fun hideProgress() {
        progressBar.hide()
    }

    override fun showVideo(details: DetailsView) {
        Timber.d { "Received details item from presenter" }

        detailsItem = mapper.mapToViewModel(details)

        prepareVideoPlayer(detailsItem!!.videoUrl)
    }

    private fun updateToolbar(title: String) {
        supportActionBar?.subtitle = title
    }

    override fun hideVideo() {
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
                    Player.STATE_IDLE -> {
                        thumbnailImage.visibility = View.VISIBLE
                        playerView.visibility = View.INVISIBLE
                    }
                    Player.STATE_READY -> {
                        thumbnailImage.visibility = View.INVISIBLE
                        playerView.visibility = View.VISIBLE
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
            prepareVideoPlayer(it.videoUrl)
        }
    }
}
