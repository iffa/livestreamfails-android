package digital.sogood.livestreamfails.mobile.ui.base

import android.content.Context
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import com.google.android.material.snackbar.Snackbar
import digital.sogood.livestreamfails.R

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
fun Snackbar.config(context: Context): Snackbar {
    val params = this.view.layoutParams as ViewGroup.MarginLayoutParams
    params.setMargins(12, 12, 12, 12)
    this.view.layoutParams = params

    this.view.background = context.getDrawable(R.drawable.bg_snackbar)

    ViewCompat.setElevation(this.view, 6f)

    return this
}