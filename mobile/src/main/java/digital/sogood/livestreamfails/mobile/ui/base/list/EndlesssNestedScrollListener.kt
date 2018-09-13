package digital.sogood.livestreamfails.mobile.ui.base.list

import androidx.core.widget.NestedScrollView

/**
 * Scroll listener that calls the provided [listener] when we think more data should be loaded.
 * Note that the receiving party should keep track of state, i.e. are we loading already or not.
 *
 * Adaptation of [EndlessScrollListener] but for [NestedScrollView].
 *
 * TODO: Improve functionality by triggering [listener] earlier.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class EndlesssNestedScrollListener(val listener: () -> Unit) : NestedScrollView.OnScrollChangeListener {
    override fun onScrollChange(v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int) {
        v?.getChildAt(0)?.let { child ->
            if (scrollY == (child.measuredHeight - v.measuredHeight)) listener()
        }
    }
}