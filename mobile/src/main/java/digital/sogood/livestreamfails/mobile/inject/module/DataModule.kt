package digital.sogood.livestreamfails.mobile.inject.module

import dagger.Binds
import dagger.Module
import digital.sogood.livestreamfails.data.DetailsDataRepository
import digital.sogood.livestreamfails.data.FailDataRepository
import digital.sogood.livestreamfails.data.GameDataRepository
import digital.sogood.livestreamfails.data.StreamerDataRepository
import digital.sogood.livestreamfails.data.executor.JobExecutor
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.domain.repository.StreamerRepository

/**
 * Module that provides all dependencies from the data layer.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class DataModule {
    @Binds
    abstract fun bindFailRepository(failDataRepository: FailDataRepository): FailRepository

    @Binds
    abstract fun bindDetailsRepository(detailsDataRepository: DetailsDataRepository): DetailsRepository

    @Binds
    abstract fun bindStreamerRepository(streamerDataRepository: StreamerDataRepository): StreamerRepository

    @Binds
    abstract fun bindGameRepository(gameDataRepository: GameDataRepository): GameRepository

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}