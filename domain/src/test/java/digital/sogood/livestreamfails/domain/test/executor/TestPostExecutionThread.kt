package digital.sogood.livestreamfails.domain.test.executor

import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import io.reactivex.Scheduler
import io.reactivex.schedulers.TestScheduler

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class TestPostExecutionThread : PostExecutionThread {
    override val scheduler: Scheduler
        get() = TestScheduler()
}