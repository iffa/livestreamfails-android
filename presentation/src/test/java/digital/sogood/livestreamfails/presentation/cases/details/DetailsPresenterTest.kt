package digital.sogood.livestreamfails.presentation.cases.details

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.interactor.cases.GetDetails
import digital.sogood.livestreamfails.domain.model.Details
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.presentation.mapper.DetailsViewMapper
import digital.sogood.livestreamfails.presentation.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.presentation.test.executor.TestThreadExecutor
import digital.sogood.livestreamfails.presentation.test.factory.DetailsFactory
import io.reactivex.Single
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class DetailsPresenterTest {
    private val threadExecutor = TestThreadExecutor()
    private val postExecutionThread = TestPostExecutionThread()

    private lateinit var contract: DetailsContract

    private lateinit var useCase: GetDetails
    private lateinit var repository: DetailsRepository
    private lateinit var viewMapper: DetailsViewMapper

    @Before
    fun setUp() {
        contract = mock()

        repository = mock()
        viewMapper = mock()

        useCase = GetDetails(repository, threadExecutor, postExecutionThread)
    }

    @Test
    fun testContractAttaches() {
        stubGetDetails(Single.just(DetailsFactory.makeDetails()))

        val presenter = DetailsPresenter(useCase, viewMapper)
        val testPresenter = TiTestPresenter(presenter)

        testPresenter.attachView(contract)

        assertTrue(presenter.isViewAttached, "View should be attached")
    }

    private fun stubGetDetails(single: Single<Details>) {
        whenever(repository.getDetails(ArgumentMatchers.any()))
                .thenReturn(single)
    }
}