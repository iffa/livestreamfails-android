package digital.sogood.livestreamfails.domain.interactor.cases

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.executor.PostExecutionThread
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.domain.test.factory.GameFactory
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GetGamesTest {
    private lateinit var useCase: GetGames

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockRepository: GameRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockRepository = mock()

        useCase = GetGames(mockRepository, mockThreadExecutor, mockPostExecutionThread)
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val fails = GameFactory.makeGameList(2)
        stubGetGames(Single.just(fails))
        val testObserver = useCase.buildUseCaseObservable(null).test()
        testObserver.assertValue(fails)
    }

    private fun stubGetGames(single: Single<List<Game>>) {
        whenever(mockRepository.getGames(ArgumentMatchers.any()))
                .thenReturn(single)
    }
}