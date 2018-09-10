package digital.sogood.livestreamfails.presentation.cases.streamer

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.domain.interactor.cases.GetStreamers
import digital.sogood.livestreamfails.domain.model.Streamer
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import digital.sogood.livestreamfails.presentation.mapper.StreamerViewMapper
import digital.sogood.livestreamfails.presentation.test.executor.TestPostExecutionThread
import digital.sogood.livestreamfails.presentation.test.executor.TestThreadExecutor
import digital.sogood.livestreamfails.presentation.test.factory.StreamerFactory
import io.reactivex.Single
import net.grandcentrix.thirtyinch.test.TiTestPresenter
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import kotlin.test.assertTrue

/**
 * @author Santeri Elo <me></me>@santeri.xyz>
 */
class StreamerPresenterTest {
    private val threadExecutor = TestThreadExecutor()
    private val postExecutionThread = TestPostExecutionThread()

    private lateinit var contract: StreamerContract

    private lateinit var useCase: GetStreamers
    private lateinit var repository: StreamerRepository
    private lateinit var viewMapper: StreamerViewMapper

    @Before
    fun setUp() {
        contract = mock()

        repository = mock()
        viewMapper = mock()

        useCase = GetStreamers(repository, threadExecutor, postExecutionThread)
    }

    @Test
    fun testContractAttaches() {
        stubGetStreamers(Single.just(StreamerFactory.makeStreamerList(2)))

        val presenter = StreamerPresenter(useCase, viewMapper)
        val testPresenter = TiTestPresenter(presenter)

        testPresenter.attachView(contract)

        assertTrue(presenter.isViewAttached, "View should be attached")
    }

    private fun stubGetStreamers(single: Single<List<Streamer>>) {
        whenever(repository.getStreamers(ArgumentMatchers.any()))
                .thenReturn(single)
    }
}