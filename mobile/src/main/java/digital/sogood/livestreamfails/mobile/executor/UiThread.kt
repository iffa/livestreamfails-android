package digital.sogood.livestreamfails.mobile.executor

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class UiThread @Inject constructor() : PostExecutionThread {
    override val scheduler: Scheduler = AndroidSchedulers.mainThread()
}