package digital.sogood.livestreamfails.presentation.cases.fail

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.interactor.cases.GetFails
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.presentation.mapper.FailViewMapper
import digital.sogood.livestreamfails.presentation.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.presentation.test.executor.TestThreadExecutor
import digital.sogood.livestreamfails.presentation.test.factory.FailFactory
import io.reactivex.Single
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailPresenterTest {
    private val threadExecutor = TestThreadExecutor()
    private val postExecutionThread = TestPostExecutionThread()

    private lateinit var contract: FailContract

    private lateinit var useCase: GetFails
    private lateinit var repository: FailRepository
    private lateinit var viewMapper: FailViewMapper

    @Before
    fun setUp() {
        contract = mock()

        repository = mock()
        viewMapper = mock()

        useCase = GetFails(repository, threadExecutor, postExecutionThread)
    }

    @Test
    fun testContractAttaches() {
        stubGetFails(Single.just(FailFactory.makeFailList(2)))

        val presenter = FailPresenter(useCase, viewMapper)
        val testPresenter = TiTestPresenter(presenter)

        testPresenter.attachView(contract)

        assertTrue(presenter.isViewAttached, "View should be attached")
    }

    private fun stubGetFails(single: Single<List<Fail>>) {
        whenever(repository.getFails(ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any(),
                ArgumentMatchers.any(), ArgumentMatchers.any(), ArgumentMatchers.any()))
                .thenReturn(single)
    }
}