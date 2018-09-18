package digital.sogood.livestreamfails.mobile.ui.menu

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.drawable.Animatable
import android.graphics.drawable.AnimatedStateListDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.lucasurbas.listitemview.ListItemView
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.R
import kotlinx.android.synthetic.main.bottom_sheet_menu.*


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class MenuDialogFragment : BottomSheetDialogFragment() {
    companion object {
        private const val SOURCE_URL = "https://github.com/iffa/livestreamfails-android"
        private const val LICENSES_URL = SOURCE_URL
        private const val FEEDBACK_URL = "https://github.com/iffa/livestreamfails-android/issues"
    }

    override fun getTheme(): Int = R.style.Theme_LivestreamFails_BottomSheet

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        versionText.text = getString(R.string.version_text, BuildConfig.VERSION_NAME)

        setupCheckBoxMenu(nsfwToggle)
        nsfwToggle.setOnClickListener {
            toggleCheckBoxMenu(it as ListItemView, true)
        }

        sourceItem.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(SOURCE_URL))
            startActivity(intent)
        }

        licensesItem.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(LICENSES_URL))
            startActivity(intent)
        }

        feedbackItem.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(FEEDBACK_URL))
            startActivity(intent)
        }
    }

    @SuppressLint("RestrictedApi")
    private fun setupCheckBoxMenu(listItemView: ListItemView) {
        val asl = AnimatedStateListDrawable()
        asl.addState(
                intArrayOf(android.R.attr.state_checked),
                AppCompatResources.getDrawable(requireContext(), R.drawable.vd_checkbox_checked)!!,
                R.id.checked)
        asl.addState(
                IntArray(0),
                AppCompatResources.getDrawable(requireContext(), R.drawable.vd_checkbox_unchecked)!!,
                R.id.unchecked)
        asl.addTransition(
                R.id.unchecked,
                R.id.checked,
                AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.avd_checkbox_unchecked_to_checked)!!,
                false)
        asl.addTransition(
                R.id.checked,
                R.id.unchecked,
                AnimatedVectorDrawableCompat.create(requireContext(), R.drawable.avd_checkbox_checked_to_unchecked)!!,
                false)

        listItemView.inflateMenu(R.menu.checkable_action_menu)
        val imageView = listItemView.findMenuItem(R.id.action_checkable).actionView as ImageView
        imageView.setImageDrawable(asl)
        asl.jumpToCurrentState()
    }

    @SuppressLint("RestrictedApi")
    private fun toggleCheckBoxMenu(listItemView: ListItemView, toggle: Boolean) {
        val drawable = if (listItemView.isChecked)
            AnimatedVectorDrawableCompat
                    .create(requireContext(), R.drawable.avd_checkbox_checked_to_unchecked)
        else
            AnimatedVectorDrawableCompat
                    .create(requireContext(), R.drawable.avd_checkbox_unchecked_to_checked)
        val imageView = listItemView.findMenuItem(R.id.action_checkable).actionView as ImageView
        imageView.setImageDrawable(drawable)
        (drawable as Animatable).start()

        if (toggle) {
            listItemView.toggle()
        }
    }
}