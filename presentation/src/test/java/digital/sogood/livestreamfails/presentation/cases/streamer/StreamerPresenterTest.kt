package digital.sogood.livestreamfails.presentation.cases.streamer

import com.nhaarman.mockito_kotlin.*
import digital.sogood.livestreamfails.domain.interactor.cases.GetStreamers
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import digital.sogood.livestreamfails.presentation.mapper.StreamerViewMapper
import digital.sogood.livestreamfails.presentation.test.factory.StreamerFactory
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test

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

        presenter.retrieveStreamers(null)

        verify(mockContract).showProgress()

        verify(mockUseCase).execute(captor.capture(), eq(null))

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

        presenter.retrieveStreamers(null)

        verify(mockUseCase).execute(captor.capture(), eq(null))

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

        presenter.retrieveStreamers(null)

        verify(mockUseCase).execute(captor.capture(), eq(null))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showStreamers(items.map { mockViewMapper.mapToView(it) })
    }

    /**
     * Retrieving triggers [StreamerContract.showEmptyState] if the resulting list
     * is empty.
     */
    @Test
    fun retrieveShowsEmptyState() {
        val items = StreamerFactory.makeStreamerList(0)

        presenter.retrieveStreamers(null)

        verify(mockUseCase).execute(captor.capture(), eq(null))

        captor.firstValue.onSuccess(items)

        verify(mockContract).showEmptyState()
    }

    /**
     * Retrieving triggers [StreamerContract.hideStreamers] and [StreamerContract.hideEmptyState]
     * if the call is not successful.
     */
    @Test
    fun retrieveHidesResultsOnError() {
        presenter.retrieveStreamers(null)

        verify(mockUseCase).execute(captor.capture(), eq(null))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).hideStreamers()
        verify(mockContract).hideEmptyState()
    }

    /**
     * Retrieving triggers [StreamerContract.showErrorState] if the call is not successful.
     */
    @Test
    fun retrieveShowsErrorState() {
        presenter.retrieveStreamers(null)

        verify(mockUseCase).execute(captor.capture(), eq(null))

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).showErrorState()
    }
}