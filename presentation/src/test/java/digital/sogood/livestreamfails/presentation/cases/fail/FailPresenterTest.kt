package digital.sogood.livestreamfails.presentation.cases.fail

import com.nhaarman.mockito_kotlin.*
import digital.sogood.livestreamfails.domain.interactor.cases.FailParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetFails
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import digital.sogood.livestreamfails.presentation.test.TestFailPresenter
import digital.sogood.livestreamfails.presentation.test.factory.FailFactory
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailPresenterTest {
    private lateinit var mockContract: FailContract
    private lateinit var mockUseCase: GetFails
    private lateinit var mockRepository: FailRepository
    private lateinit var mockViewMapper: FailViewMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Fail>>>
    private lateinit var presenter: FailPresenter
    private lateinit var testPresenter: TiTestPresenter<FailContract>

    @Before
    fun setUp() {
        captor = argumentCaptor()
        mockContract = mock()
        mockUseCase = mock()
        mockRepository = mock()
        mockViewMapper = mock()

        // Create presenter and attach  the view to it
        presenter = TestFailPresenter(mockUseCase, mockViewMapper)

        testPresenter = TiTestPresenter(presenter)
        testPresenter.attachView(mockContract)
    }

    /**
     * Retrieving triggers [FailContract.showProgress] and [FailContract.hideProgress].
     */
    @Test
    fun retrieveShowsAndHidesProgress() {
        val items = FailFactory.makeFailList(2)

        presenter.retrieveFails()

        verify(mockContract).showProgress()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideProgress()
    }

    /**
     * Retrieving triggers [FailContract.hideEmptyState] and [FailContract.hideErrorState]
     * if successful.
     */
    @Test
    fun retrieveHidesEmptyAndErrorState() {
        val items = FailFactory.makeFailList(2)

        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideEmptyState()
        verify(mockContract).hideErrorState()
    }

    /**
     * Retrieving triggers [FailContract.showFails] if successful.
     */
    @Test
    fun retrieveShowsResults() {
        val items = FailFactory.makeFailList(2)

        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showFails(items.map { mockViewMapper.mapToView(it) })
    }

    /**
     * Retrieving triggers [FailContract.showEmptyState] if the resulting list
     * is empty, and the current page is 0.
     */
    @Test
    fun retrieveShowsEmptyState() {
        val items = FailFactory.makeFailList(0)

        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showEmptyState()
    }

    /**
     * Retrieving triggers [FailContract.hideFails] and [FailContract.hideEmptyState]
     * if the call is not successful.
     */
    @Test
    fun retrieveHidesResultsOnError() {
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).hideFails()
        verify(mockContract).hideEmptyState()
    }

    /**
     * Retrieving triggers [FailContract.showErrorState] if the call is not successful.
     */
    @Test
    fun retrieveShowsErrorState() {
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).showErrorState()
    }

    /**
     * Retrieving increments [FailPresenter.currentPage].
     */
    @Test
    fun retrieveChangesCurrentPage() {
        val items = FailFactory.makeFailList(2)

        // Page 1
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)

        // Page 2
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        assertEquals(1, presenter.currentPage)
    }

    /**
     * Retrieving an empty page greater than 0 triggers [FailContract.showNoMoreResultsState].
     */
    @Test
    fun retrieveShowsNoMoreResults() {
        val items = FailFactory.makeFailList(2)

        // Page 0, should have results
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)
        verify(mockContract).showFails(items.map { mockViewMapper.mapToView(it) })

        // Page 1, should have no results (show no more results state)
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(emptyList())

        assertEquals(1, presenter.currentPage)
        verify(mockContract).showNoMoreResultsState()
    }

    /**
     * [FailContract.clearFails] should be triggered and [FailPresenter.currentPage] reset to 0
     * when parameters change.
     */
    @Test
    fun retrieveChangedParameters() {
        val items = FailFactory.makeFailList(2)

        // Page 0, should have results
        presenter.retrieveFails()

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)
        verify(mockContract).showFails(items.map { mockViewMapper.mapToView(it) })

        // Attempt at page 1, but with different parameters
        presenter.onTimeFrameChanged(TimeFrame.ALL_TIME)

        assertEquals(TimeFrame.ALL_TIME, presenter.timeFrame)

        verify(mockUseCase).execute(captor.capture(), eq(FailParams(presenter.currentPage, presenter.timeFrame, presenter.order, presenter.nsfw)))

        captor.firstValue.onSuccess(items)

        // Expect current page to be 0, as it should've been reset
        assertEquals(0, presenter.currentPage)

        verify(mockContract).clearFails()
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