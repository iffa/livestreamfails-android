package digital.sogood.livestreamfails.domain.test.executor

import digital.sogood.livestreamfails.domain.executor.ThreadExecutor

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class TestThreadExecutor : ThreadExecutor {
    override fun execute(command: Runnable?) {
        command?.run()
    }
}