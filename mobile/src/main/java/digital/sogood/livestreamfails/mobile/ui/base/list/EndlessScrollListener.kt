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
class EndlessScrollListener(val listener: () -> Unit) : RecyclerView.OnScrollListener() {
    private val scrollThreshold = 2

    @Synchronized
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            val count = layoutManager.itemCount
            val lastVisibleItemIndex = layoutManager.findLastVisibleItemPosition()

            if (count <= (lastVisibleItemIndex + scrollThreshold)) listener()
        }
    }
}