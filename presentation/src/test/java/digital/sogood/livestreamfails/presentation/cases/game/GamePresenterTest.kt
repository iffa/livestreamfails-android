package digital.sogood.livestreamfails.presentation.cases.game

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.interactor.cases.GetGames
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.presentation.mapper.GameViewMapper
import digital.sogood.livestreamfails.presentation.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.presentation.test.executor.TestThreadExecutor
import digital.sogood.livestreamfails.presentation.test.factory.GameFactory
import io.reactivex.Single
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GamePresenterTest {
    private val threadExecutor = TestThreadExecutor()
    private val postExecutionThread = TestPostExecutionThread()

    private lateinit var contract: GameContract

    private lateinit var useCase: GetGames
    private lateinit var repository: GameRepository
    private lateinit var viewMapper: GameViewMapper

    @Before
    fun setUp() {
        contract = mock()

        repository = mock()
        viewMapper = mock()

        useCase = GetGames(repository, threadExecutor, postExecutionThread)
    }

    @Test
    fun testContractAttaches() {
        stubGetGames(Single.just(GameFactory.makeGameList(2)))

        val presenter = GamePresenter(useCase, viewMapper)
        val testPresenter = TiTestPresenter(presenter)

        testPresenter.attachView(contract)

        assertTrue(presenter.isViewAttached, "View should be attached")
    }

    private fun stubGetGames(single: Single<List<Game>>) {
        whenever(repository.getGames(ArgumentMatchers.any()))
                .thenReturn(single)
    }
}