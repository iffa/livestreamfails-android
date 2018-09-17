package digital.sogood.livestreamfails.mobile.ui.fail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.ajalt.timberkt.Timber
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import digital.sogood.livestreamfails.mobile.ui.base.list.ListAdapterWithHeader

/**
 * TODO: Split into multiple classes and clean up
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailAdapter constructor(private val selectedTimeFrame: TimeFrame,
                              private val selectedOrder: Order,
                              private val timeframeListener: (TimeFrame) -> Unit,
                              private val orderListener: (Order) -> Unit,
                              private val itemClickListener: (item: FailViewModel, thumbnail: ImageView) -> Unit)
    : ListAdapterWithHeader<FailViewModel, RecyclerView.ViewHolder>(ItemDiffCallback()) {
    private val items: MutableList<FailViewModel> = mutableListOf()

    /**
     * Adds items to the adapter instead of replacing the current data set. Calls [submitList].
     */
    fun addItems(list: List<FailViewModel>) {
        items.addAll(list)
        submitList(items.toList())
    }

    /**
     * Clears the current data set. Calls [submitList].
     */
    fun clear() {
        items.clear()
        submitList(items.toList())
    }

    fun getItems(): List<FailViewModel> {
        return items
    }

    override fun submitList(list: List<FailViewModel>?) {
        items.clear()
        list?.let { items.addAll(it) }

        super.submitList(list)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            R.layout.view_holder_fail_header
        } else {
            R.layout.view_holder_fail_alt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(viewType, parent, false)

        return when (viewType) {
            R.layout.view_holder_fail_header -> HeaderViewHolder(view)
            else -> ItemViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            R.layout.view_holder_fail_header -> (holder as HeaderViewHolder).bind()
            else -> (holder as ItemViewHolder).bind(getItem(position))
        }
    }

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val thumbnailImage: ImageView = view.findViewById(R.id.thumbnailImage)
        private val titleText: TextView = view.findViewById(R.id.titleText)
        private val subtitleText: TextView = view.findViewById(R.id.subtitleText)
        private val pointsText: TextView = view.findViewById(R.id.pointsText)
        private val nsfwText: TextView = view.findViewById(R.id.nsfwText)
        private val actionMenuButton: ImageButton = view.findViewById(R.id.actionMenuButton)
        private val cardView: MaterialCardView = view.findViewById(R.id.cardView)

        /**
         * TODO: Handle string formatting beforehand?
         */
        fun bind(item: FailViewModel) {
            titleText.text = item.title

            val hasSubtitleData = !item.streamer.isNullOrEmpty() || !item.game.isNullOrEmpty()
            when (hasSubtitleData) {
                true -> subtitleText.text = view.resources.getString(R.string.tv_fail_subtitle, item.streamer, item.game)
                false -> subtitleText.text = view.resources.getString(R.string.tv_fail_subtitle_none)
            }

            pointsText.text = view.resources.getString(R.string.tv_fail_points, item.points)

            nsfwText.visibility = when (item.nsfw) {
                true -> View.VISIBLE
                false -> View.GONE
            }

            Glide.with(thumbnailImage)
                    .load(item.thumbnailUrl)
                    .apply(RequestOptions().centerCrop())
                    .into(thumbnailImage)

            cardView.setOnClickListener {
                itemClickListener(item, thumbnailImage)
            }

            // Shared element transition setup
            ViewCompat.setTransitionName(thumbnailImage, item.postId.toString())
        }
    }

    inner class HeaderViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val timeframeChips: ChipGroup = view.findViewById(R.id.timeFrameGroup)
        private val orderChips: ChipGroup = view.findViewById(R.id.orderGroup)

        fun bind() {
            if (timeframeChips.checkedChipId == -1) {
                Timber.d { "Checking correct time frame chip $selectedTimeFrame" }
                when (selectedTimeFrame) {
                    TimeFrame.DAY -> timeframeChips.check(R.id.todayFilterChip)
                    TimeFrame.WEEK -> timeframeChips.check(R.id.thisWeekFilterChip)
                    TimeFrame.MONTH -> timeframeChips.check(R.id.thisMonthFilterChip)
                    TimeFrame.YEAR -> timeframeChips.check(R.id.thisYearFilterChip)
                    TimeFrame.ALL_TIME -> timeframeChips.check(R.id.allTimeFilterChip)
                }
            }

            if (orderChips.checkedChipId == -1) {
                Timber.d { "Checking correct order chip $selectedOrder" }
                when (selectedOrder) {
                    Order.HOT -> orderChips.check(R.id.hotFilterChip)
                    Order.TOP -> orderChips.check(R.id.topFilterChip)
                    Order.NEW -> orderChips.check(R.id.newFilterChip)
                    Order.RANDOM -> orderChips.check(R.id.randomFilterChip)
                }
            }

            timeframeChips.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.todayFilterChip -> timeframeListener(TimeFrame.DAY)
                    R.id.thisWeekFilterChip -> timeframeListener(TimeFrame.WEEK)
                    R.id.thisMonthFilterChip -> timeframeListener(TimeFrame.MONTH)
                    R.id.thisYearFilterChip -> timeframeListener(TimeFrame.YEAR)
                    R.id.allTimeFilterChip -> timeframeListener(TimeFrame.ALL_TIME)
                }
            }
            orderChips.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.newFilterChip -> orderListener(Order.NEW)
                    R.id.hotFilterChip -> orderListener(Order.HOT)
                    R.id.topFilterChip -> orderListener(Order.TOP)
                    R.id.randomFilterChip -> orderListener(Order.RANDOM)
                }
            }
        }
    }

    class ItemDiffCallback : DiffUtil.ItemCallback<FailViewModel>() {
        override fun areItemsTheSame(oldItem: FailViewModel, newItem: FailViewModel): Boolean {
            return oldItem.postId == newItem.postId
        }

        override fun areContentsTheSame(oldItem: FailViewModel, newItem: FailViewModel): Boolean {
            return oldItem == newItem
        }

    }
}