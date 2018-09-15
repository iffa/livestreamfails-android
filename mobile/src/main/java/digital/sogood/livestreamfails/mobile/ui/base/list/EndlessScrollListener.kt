package digital.sogood.livestreamfails.mobile.ui.base.list

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Scroll listener that calls the provided [listener] when we think more data should be loaded.
 * Note that the receiving party should keep track of state, i.e. are we loading already or not.
 *
 * Only works with [LinearLayoutManager].
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class EndlessScrollListener(private val listener: () -> Unit) : RecyclerView.OnScrollListener() {
    companion object {
        private const val MARGIN = 4
    }

    @Synchronized
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPos = layoutManager.findFirstVisibleItemPosition()

            if ((visibleItemCount + firstVisibleItemPos) >= totalItemCount - MARGIN
                    && firstVisibleItemPos >= 0
                    && totalItemCount >= 20) {
                listener()
            }
        }
    }
}