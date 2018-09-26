package digital.sogood.livestreamfails.presentation

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import io.reactivex.Observable

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface SettingsService {
    fun shouldShowNsfwContent(): Boolean

    fun getDefaultTimeFrame(): TimeFrame

    fun getDefaultOrder(): Order

    fun shouldShowNsfwContentObservable(): Observable<Boolean>

    fun setDefaultTimeFrame(timeFrame: TimeFrame)

    fun setDefaultOrder(order: Order)

    fun setShowNsfwContent(showNsfw: Boolean)
}