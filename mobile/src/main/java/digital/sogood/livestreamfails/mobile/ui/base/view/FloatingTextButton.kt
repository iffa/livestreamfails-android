package digital.sogood.livestreamfails.mobile.ui.base.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import androidx.coordinatorlayout.widget.CoordinatorLayout
import ru.dimorinny.floatingtextbutton.FloatingTextButton

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FloatingTextButton(context: Context?, private val attrs: AttributeSet?) : FloatingTextButton(context, attrs), CoordinatorLayout.AttachedBehavior {
    override fun getBehavior(): CoordinatorLayout.Behavior<*> {
        return FloatingTextButtonBehavior(context, attrs)
    }

    fun animateIn() {
        val anim = ScaleAnimation(0f, 1f, 0f, 1f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationStart(animation: Animation?) {
                visibility = View.VISIBLE
            }

        })
        startAnimation(anim)
    }

    fun animateOut() {
        val anim = ScaleAnimation(1f, 0f, 1f, 0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        anim.duration = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = View.INVISIBLE
            }

            override fun onAnimationStart(animation: Animation?) {
            }

        })
        startAnimation(anim)
    }
}