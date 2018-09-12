package digital.sogood.livestreamfails.mobile.inject.module

import com.nhaarman.mockito_kotlin.mock
import dagger.Binds
import dagger.Module
import dagger.Provides
import digital.sogood.livestreamfails.data.executor.JobExecutor
import digital.sogood.livestreamfails.domain.executor.ThreadExecutor
import digital.sogood.livestreamfails.domain.repository.DetailsRepository
import digital.sogood.livestreamfails.domain.repository.FailRepository
import digital.sogood.livestreamfails.domain.repository.GameRepository
import digital.sogood.livestreamfails.domain.repository.StreamerRepository
import javax.inject.Singleton

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class TestDataModule {
    @Module
    companion object {
        @Singleton
        @Provides
        @JvmStatic
        fun provideFailRepository(): FailRepository {
            return mock()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideDetailsRepository(): DetailsRepository {
            return mock()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideGameRepository(): GameRepository {
            return mock()
        }

        @Singleton
        @Provides
        @JvmStatic
        fun provideStreamerRepository(): StreamerRepository {
            return mock()
        }
    }

    @Binds
    abstract fun bindThreadExecutor(jobExecutor: JobExecutor): ThreadExecutor
}