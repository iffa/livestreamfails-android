package digital.sogood.livestreamfails.mobile.ui.base.view

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * [FloatingActionButton], but with the style and swag of [MaterialButton].
 *
 * See: https://material.io/design/components/buttons-floating-action-button.html#extended-fab
 *
 * TODO: Implementation of button logic
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
class MaterialFloatingButton(context: Context?, attrs: AttributeSet?) : MaterialButton(context, attrs) {
    private var userSetVisibility: Int = visibility

    override fun setVisibility(visibility: Int) {
        internalSetVisibility(visibility, true)
    }

    private fun internalSetVisibility(visibility: Int, fromUser: Boolean) {
        super.setVisibility(visibility)

        if (fromUser) {
            userSetVisibility = visibility
        }
    }

    fun getUserSetVisibility(): Int = userSetVisibility
}