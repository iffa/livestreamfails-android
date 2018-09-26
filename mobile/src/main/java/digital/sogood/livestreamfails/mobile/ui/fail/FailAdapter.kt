package digital.sogood.livestreamfails.mobile.ui.fail

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
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
 * TODO: Handle string formatting beforehand?
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailAdapter constructor(private val selectedTimeFrame: TimeFrame,
                              private val selectedOrder: Order,
                              private val timeframeListener: (TimeFrame) -> Unit,
                              private val orderListener: (Order) -> Unit,
                              private val itemClickListener: (item: FailViewModel) -> Unit)
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
            else -> {
                val holder = ItemViewHolder(view)
                holder.cardView.setOnClickListener {
                    val position = holder.adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        itemClickListener(getItem(holder.adapterPosition))
                    }
                }
                holder.actionMenuButton.setOnClickListener {
                    val position = holder.adapterPosition

                    Timber.d { "Action menu button clicked @ pos $position" }
                    if (position != RecyclerView.NO_POSITION) {
                        showOverflowMenu(it, listener = {
                            startShareIntent(getItem(position), holder.view.context)
                        })
                    }
                }

                return holder
            }
        }
    }

    private fun startShareIntent(item: FailViewModel, context: Context) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, item.detailsUrl)
            type = "text/plain"
        }
        context.startActivity(Intent.createChooser(intent, context.resources.getText(R.string.send_to)))
    }

    private fun showOverflowMenu(view: View, listener: () -> Unit) {
        val popup = PopupMenu(view.context, view)

        popup.inflate(R.menu.view_holder_fail)
        popup.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_share -> {
                    listener()
                    true
                }
                else -> false
            }
        }

        popup.show()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (getItemViewType(position)) {
            R.layout.view_holder_fail_header -> (holder as HeaderViewHolder).bind()
            else -> {
                (holder as ItemViewHolder).bind(getItem(position))
                showEnterAnimation(holder.view, position)
            }
        }
    }

    private var lastPosition = -1
    private fun showEnterAnimation(view: View, position: Int) {
        if (position > lastPosition) {
            val anim = ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
            anim.duration = 100
            view.startAnimation(anim)
            lastPosition = position
        }
    }

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val titleText: TextView = view.findViewById(R.id.titleText)
        private val subtitleText: TextView = view.findViewById(R.id.subtitleText)
        private val pointsText: TextView = view.findViewById(R.id.pointsText)
        private val nsfwText: TextView = view.findViewById(R.id.nsfwText)
        val cardView: MaterialCardView = view.findViewById(R.id.cardView)
        val actionMenuButton: ImageButton = view.findViewById(R.id.actionMenuButton)
        val thumbnailImage: ImageView = view.findViewById(R.id.thumbnailImage)

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