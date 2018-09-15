package digital.sogood.livestreamfails.mobile.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import digital.sogood.livestreamfails.mobile.ui.base.view.FloatingTextButton

/**
 * Custom behavior for [FloatingTextButton] that hides it when scrolled to the top, and shows it
 * when scrolling down. Useful for cases where the button is used e.g. as a "scroll to top" button.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class FloatingTextButtonBehavior(context: Context, attrs: AttributeSet) : CoordinatorLayout.Behavior<FloatingTextButton>(context, attrs) {
    override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingTextButton, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
        if (axes == ViewCompat.SCROLL_AXIS_VERTICAL) return true
        return super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type)
    }

    override fun onNestedScroll(coordinatorLayout: CoordinatorLayout, child: FloatingTextButton, target: View, dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, type: Int) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type)

        if (dyConsumed > 0 && child.visibility == View.INVISIBLE) {
            child.animateIn()
        } else if (dyConsumed < 0 && child.visibility == View.VISIBLE) {
            child.animateOut()
        }
    }
}