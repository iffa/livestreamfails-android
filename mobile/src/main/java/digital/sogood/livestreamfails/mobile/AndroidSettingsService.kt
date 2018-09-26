package digital.sogood.livestreamfails.mobile

import android.content.Context
import com.afollestad.rxkprefs.RxkPrefs
import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.presentation.SettingsService
import io.reactivex.Observable
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class AndroidSettingsService @Inject constructor(context: Context) : SettingsService {
    companion object {
        private const val PREFS_GENERAL_KEY = "prefs_general"

        private const val PREFS_SHOW_NSFW = "show_nsfw"
        private const val PREFS_TIME_FRAME = "default_time_frame"
        private const val PREFS_ORDER = "default_order"
        private const val PREFS_NIGHT = "night_mode"
    }

    private val prefs = RxkPrefs(context, PREFS_GENERAL_KEY)
    private val showNsfwContent = prefs.boolean(PREFS_SHOW_NSFW, false)
    private val defaultTimeFrame = prefs.string(PREFS_TIME_FRAME, TimeFrame.DAY.name)
    private val defaultOrder = prefs.string(PREFS_ORDER, Order.HOT.name)
    private val nightMode = prefs.boolean(PREFS_NIGHT, false)

    override fun shouldShowNsfwContent(): Boolean = showNsfwContent.get()

    override fun shouldShowNsfwContentObservable(): Observable<Boolean> = showNsfwContent.observe()

    override fun getDefaultTimeFrame(): TimeFrame = TimeFrame.valueOf(defaultTimeFrame.get())

    override fun getDefaultOrder(): Order = Order.valueOf(defaultOrder.get())

    override fun setDefaultTimeFrame(timeFrame: TimeFrame) = defaultTimeFrame.set(timeFrame.name)

    override fun setDefaultOrder(order: Order) = defaultOrder.set(order.name)

    override fun setShowNsfwContent(showNsfw: Boolean) = showNsfwContent.set(showNsfw)

    override fun isNightModeEnabled(): Boolean = nightMode.get()

    override fun isNightModeEnabledObservable(): Observable<Boolean> = nightMode.observe()

    override fun setNightModeEnabled(night: Boolean) = nightMode.set(night)
}