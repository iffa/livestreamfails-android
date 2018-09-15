package digital.sogood.livestreamfails.mobile.ui.base

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import ru.dimorinny.floatingtextbutton.FloatingTextButton

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
            animateIn(child)
        } else if (dyConsumed < 0 && child.visibility == View.VISIBLE) {
            animateOut(child)
        }
    }

    private fun animateOut(button: FloatingTextButton) {
        val anim = ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = button.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                button.visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        button.startAnimation(anim)
    }

    private fun animateIn(button: FloatingTextButton) {
        val anim = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = button.resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                button.visibility = View.VISIBLE
            }

        })
        button.startAnimation(anim)
    }
}