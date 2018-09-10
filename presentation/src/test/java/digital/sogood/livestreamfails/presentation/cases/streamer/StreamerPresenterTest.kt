package digital.sogood.livestreamfails.presentation.cases.streamer

import com.nhaarman.mockito_kotlin.*
import digital.sogood.livestreamfails.domain.interactor.cases.GetStreamers
import digital.sogood.livestreamfails.domain.interactor.cases.StreamerParams
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import digital.sogood.livestreamfails.presentation.mapper.StreamerViewMapper
import digital.sogood.livestreamfails.presentation.test.factory.StreamerFactory
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class StreamerPresenterTest {
    private lateinit var mockContract: StreamerContract
    private lateinit var mockUseCase: GetStreamers
    private lateinit var mockRepository: StreamerRepository
    private lateinit var mockViewMapper: StreamerViewMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<List<Streamer>>>
    private lateinit var presenter: StreamerPresenter
    private lateinit var testPresenter: TiTestPresenter<StreamerContract>

    @Before
    fun setUp() {
        captor = argumentCaptor()
        mockContract = mock()
        mockUseCase = mock()
        mockRepository = mock()
        mockViewMapper = mock()

        // Create presenter and attach  the view to it
        presenter = StreamerPresenter(mockUseCase, mockViewMapper)
        testPresenter = TiTestPresenter(presenter)
        testPresenter.attachView(mockContract)
    }

    /**
     * Retrieving triggers [StreamerContract.showProgress] and [StreamerContract.hideProgress].
     */
    @Test
    fun retrieveShowsAndHidesProgress() {
        val items = StreamerFactory.makeStreamerList(2)

        presenter.retrieveStreamers()

        verify(mockContract).showProgress()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideProgress()
    }

    /**
     * Retrieving triggers [StreamerContract.hideEmptyState] and [StreamerContract.hideErrorState]
     * if successful.
     */
    @Test
    fun retrieveHidesEmptyAndErrorState() {
        val items = StreamerFactory.makeStreamerList(2)

        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(items)

        verify(mockContract).hideEmptyState()
        verify(mockContract).hideErrorState()
    }

    /**
     * Retrieving triggers [StreamerContract.showStreamers] if successful.
     */
    @Test
    fun retrieveShowsResults() {
        val items = StreamerFactory.makeStreamerList(2)

        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(items)

        verify(mockContract).showStreamers(items.map { mockViewMapper.mapToView(it) })
    }

    /**
     * Retrieving triggers [StreamerContract.showEmptyState] if the resulting list
     * is empty, and the current page is 0.
     */
    @Test
    fun retrieveShowsEmptyState() {
        val items = StreamerFactory.makeStreamerList(0)

        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(items)

        verify(mockContract).showEmptyState()
    }

    /**
     * Retrieving triggers [StreamerContract.hideStreamers] and [StreamerContract.hideEmptyState]
     * if the call is not successful.
     */
    @Test
    fun retrieveHidesResultsOnError() {
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).hideStreamers()
        verify(mockContract).hideEmptyState()
    }

    /**
     * Retrieving triggers [StreamerContract.showErrorState] if the call is not successful.
     */
    @Test
    fun retrieveShowsErrorState() {
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).showErrorState()
    }

    /**
     * Retrieving increments [StreamerPresenter.currentPage].
     */
    @Test
    fun retrieveChangesCurrentPage() {
        val items = StreamerFactory.makeStreamerList(2)

        // Page 1
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), eq(StreamerParams(0)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)

        // Page 2
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), eq(StreamerParams(1)))

        captor.firstValue.onSuccess(items)

        assertEquals(1, presenter.currentPage)
    }

    /**
     * Retrieving a page greater than 0 triggers [StreamerContract.showNoMoreResultsState].
     */
    @Test
    fun retrieveShowsNoMoreResults() {
        val items = StreamerFactory.makeStreamerList(2)

        // Page 0, should have results
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), eq(StreamerParams(0)))

        captor.firstValue.onSuccess(items)

        assertEquals(0, presenter.currentPage)
        verify(mockContract).showStreamers(items.map { mockViewMapper.mapToView(it) })

        // Page 1, should have no results (show no more results state)
        presenter.retrieveStreamers()

        verify(mockUseCase).execute(captor.capture(), eq(StreamerParams(1)))

        captor.firstValue.onSuccess(emptyList())

        assertEquals(1, presenter.currentPage)
        verify(mockContract).showNoMoreResultsState()
    }
}