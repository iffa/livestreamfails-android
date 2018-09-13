package digital.sogood.livestreamfails.mobile.ui.fail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.mobile.model.FailViewModel
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailAdapter @Inject constructor(itemDiffCallback: ItemDiffCallback)
    : ListAdapter<FailViewModel, FailAdapter.ViewHolder>(itemDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.view_holder_fail_alt, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val thumbnailImage: ImageView = view.findViewById(R.id.thumbnailImage)
        val titleText: TextView = view.findViewById(R.id.titleText)
        val subtitleText: TextView = view.findViewById(R.id.subtitleText)
        val pointsText: TextView = view.findViewById(R.id.pointsText)
        val nsfwText: TextView = view.findViewById(R.id.nsfwText)
        val actionMenuButton: ImageButton = view.findViewById(R.id.actionMenuButton)

        /**
         * TODO: Handle string formatting beforehand?
         */
        fun bind(item: FailViewModel) {
            titleText.text = item.title

            subtitleText.visibility = if (item.streamer.isNullOrEmpty() || item.game.isNullOrEmpty()) View.GONE else View.VISIBLE
            subtitleText.text = view.resources.getString(R.string.tv_fail_subtitle, item.streamer, item.game)

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

    class ItemDiffCallback @Inject constructor() : DiffUtil.ItemCallback<FailViewModel>() {
        override fun areItemsTheSame(oldItem: FailViewModel, newItem: FailViewModel): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: FailViewModel, newItem: FailViewModel): Boolean {
            return oldItem == newItem
        }

    }
}