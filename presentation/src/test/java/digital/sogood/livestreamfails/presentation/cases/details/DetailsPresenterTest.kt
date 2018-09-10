package digital.sogood.livestreamfails.presentation.cases.details

import com.nhaarman.mockito_kotlin.KArgumentCaptor
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import digital.sogood.livestreamfails.domain.interactor.cases.DetailsParams
import digital.sogood.livestreamfails.domain.interactor.cases.GetDetails
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import digital.sogood.livestreamfails.presentation.test.factory.DetailsFactory
import io.reactivex.observers.DisposableSingleObserver
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsPresenterTest {
    private lateinit var mockContract: DetailsContract
    private lateinit var mockUseCase: GetDetails
    private lateinit var mockRepository: DetailsRepository
    private lateinit var mockViewMapper: DetailsViewMapper

    private lateinit var captor: KArgumentCaptor<DisposableSingleObserver<Details>>
    private lateinit var presenter: DetailsPresenter
    private lateinit var testPresenter: TiTestPresenter<DetailsContract>

    @Before
    fun setUp() {
        captor = argumentCaptor()
        mockContract = mock()
        mockUseCase = mock()
        mockRepository = mock()
        mockViewMapper = mock()

        // Create presenter and attach  the view to it
        presenter = DetailsPresenter(mockUseCase, mockViewMapper)
        testPresenter = TiTestPresenter(presenter)
        testPresenter.attachView(mockContract)
    }

    /**
     * Retrieving triggers [DetailsContract.showProgress] and [DetailsContract.hideProgress].
     */
    @Test
    fun retrieveShowsAndHidesProgress() {
        val item = DetailsFactory.makeDetails()

        presenter.retrieveDetails(DetailsParams(0))

        verify(mockContract).showProgress()

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(item)

        verify(mockContract).hideProgress()
    }

    /**
     * Retrieving triggers [DetailsContract.hideErrorState]
     * if successful.
     */
    @Test
    fun retrieveHidesEmptyAndErrorState() {
        val item = DetailsFactory.makeDetails()

        presenter.retrieveDetails(DetailsParams(0))

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(item)

        verify(mockContract).hideErrorState()
    }

    /**
     * Retrieving triggers [DetailsContract.showDetails] if successful.
     */
    @Test
    fun retrieveShowsResults() {
        val item = DetailsFactory.makeDetails()

        presenter.retrieveDetails(DetailsParams(0))

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onSuccess(item)

        verify(mockContract).showDetails(mockViewMapper.mapToView(item))
    }

    /**
     * Retrieving triggers [DetailsContract.showErrorState] if the call is not successful.
     */
    @Test
    fun retrieveShowsErrorState() {
        presenter.retrieveDetails(DetailsParams(0))

        verify(mockUseCase).execute(captor.capture(), ArgumentMatchers.any())

        captor.firstValue.onError(RuntimeException())

        verify(mockContract).showErrorState()
    }
}