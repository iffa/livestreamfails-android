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
import com.github.ajalt.timberkt.Timber
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lucasurbas.listitemview.ListItemView
import dagger.android.support.DaggerAppCompatDialogFragment
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.presentation.SettingsService
import kotlinx.android.synthetic.main.bottom_sheet_menu.*
import javax.inject.Inject


/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class MenuDialogFragment : DaggerAppCompatDialogFragment() {
    companion object {
        private const val SOURCE_URL = "https://github.com/iffa/livestreamfails-android"
        private const val LICENSES_URL = SOURCE_URL
        private const val FEEDBACK_URL = "https://github.com/iffa/livestreamfails-android/issues"
    }

    @Inject
    lateinit var settings: SettingsService

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

        setupCheckBoxMenu(nightModeToggle)
        nightModeToggle.setOnClickListener {
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

    override fun onStop() {
        super.onStop()

        settings.setShowNsfwContent(nsfwToggle.isChecked)
        settings.setNightModeEnabled(nightModeToggle.isChecked)
    }

    override fun onStart() {
        super.onStart()

        updateCheckedState()
    }

    private fun updateCheckedState() {
        val showNsfw = settings.shouldShowNsfwContent()
        val nightModeEnabled = settings.isNightModeEnabled()

        Timber.d { "Show NSFW should be checked: $showNsfw" }
        Timber.d { "Night mode should be checked: $nightModeEnabled" }

        if ((showNsfw && !nsfwToggle.isChecked) || (!showNsfw && nsfwToggle.isChecked)) {
            toggleCheckBoxMenu(nsfwToggle, true)
        }

        if ((nightModeEnabled && !nightModeToggle.isChecked) || (!nightModeEnabled && nightModeToggle.isChecked)) {
            toggleCheckBoxMenu(nightModeToggle, true)
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