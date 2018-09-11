package digital.sogood.livestreamfails.presentation.cases.game

import com.nhaarman.mockito_kotlin.*
import digital.sogood.livestreamfails.domain.interactor.cases.GameParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetGames
import digital.sogood.livestreamfails.domain.model.Game
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.presentation.mapper.GameViewMapper
import digital.sogood.livestreamfails.presentation.test.factory.GameFactory
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class GamePresenterTest {
    private lateinit var mockContract: GameContract
    private lateinit var mockUseCase: GetGames
    private lateinit var mockRepository: GameRepository
    private lateinit var mockViewMapper: GameViewMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Game>>>
    private lateinit var presenter: GamePresenter
    private lateinit var testPresenter: TiTestPresenter<GameContract>

    @Before
    fun setUp() {
        captor = argumentCaptor()
        mockContract = mock()
        mockUseCase = mock()
        mockRepository = mock()
        mockViewMapper = mock()

        // Create presenter and attach  the view to it
        presenter = GamePresenter(mockUseCase, mockViewMapper)
        testPresenter = TiTestPresenter(presenter)
        testPresenter.attachView(mockContract)
    }

    /**
     * Retrieving triggers [GameContract.showProgress] and [GameContract.hideProgress].
     */
    @Test
    fun retrieveShowsAndHidesProgress() {
        val items = GameFactory.makeGameList(2)

        presenter.retrieveGames()

        verify(mockContract).showProgress()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideProgress()
    }

    /**
     * Retrieving triggers [GameContract.hideEmptyState] and [GameContract.hideErrorState]
     * if successful.
     */
    @Test
    fun retrieveHidesEmptyAndErrorState() {
        val items = GameFactory.makeGameList(2)

        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideEmptyState()
        verify(mockContract).hideErrorState()
    }

    /**
     * Retrieving triggers [GameContract.showGames] if successful.
     */
    @Test
    fun retrieveShowsResults() {
        val items = GameFactory.makeGameList(2)

        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showGames(items.map { mockViewMapper.mapToView(it) })
    }

    /**
     * Retrieving triggers [GameContract.showEmptyState] if the resulting list
     * is empty, and the current page is 0.
     */
    @Test
    fun retrieveShowsEmptyState() {
        val items = GameFactory.makeGameList(0)

        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showEmptyState()
    }

    /**
     * Retrieving triggers [GameContract.hideGames] and [GameContract.hideEmptyState]
     * if the call is not successful.
     */
    @Test
    fun retrieveHidesResultsOnError() {
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).hideGames()
        verify(mockContract).hideEmptyState()
    }

    /**
     * Retrieving triggers [GameContract.showErrorState] if the call is not successful.
     */
    @Test
    fun retrieveShowsErrorState() {
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).showErrorState()
    }

    /**
     * Retrieving increments [GamePresenter.currentPage].
     */
    @Test
    fun retrieveChangesCurrentPage() {
        val items = GameFactory.makeGameList(2)

        // Page 1
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)

        // Page 2
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        assertEquals(1, presenter.currentPage)
    }

    /**
     * Retrieving a page greater than 0 triggers [GameContract.showNoMoreResultsState].
     */
    @Test
    fun retrieveShowsNoMoreResults() {
        val items = GameFactory.makeGameList(2)

        // Page 0, should have results
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)
        verify(mockContract).showGames(items.map { mockViewMapper.mapToView(it) })

        // Page 1, should have no results (show no more results state)
        presenter.retrieveGames()

        verify(mockUseCase).execute(captor.capture(), eq(GameParams(presenter.currentPage)))

        captor.firstValue.onSuccess(emptyList())

        assertEquals(1, presenter.currentPage)
        verify(mockContract).showNoMoreResultsState()
    }

    @Test
    fun onDestroyDisposesUseCase() {
        testPresenter.destroy()

        verify(mockUseCase).dispose()
    }

    @Test
    fun setCurrentPageTest() {
        presenter.currentPage = 10

        assertEquals(10, presenter.currentPage)

        presenter.currentPage = -1

        assertEquals(-1, presenter.currentPage)
    }
}