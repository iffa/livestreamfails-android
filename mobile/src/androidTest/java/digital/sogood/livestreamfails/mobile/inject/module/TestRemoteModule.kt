package digital.sogood.livestreamfails.mobile.inject.module

import com.nhaarman.mockito_kotlin.mock
import dagger.Module
import dagger.Provides
import digital.sogood.livestreamfails.data.repository.details.DetailsRemote
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.data.repository.game.GameRemote
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import digital.sogood.livestreamfails.remote.service.details.DetailsService
import digital.sogood.livestreamfails.remote.service.fail.FailService
import digital.sogood.livestreamfails.remote.service.game.GameService
import digital.sogood.livestreamfails.remote.service.streamer.StreamerService
import javax.inject.Singleton

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
object TestRemoteModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideFailRemote(): FailRemote {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideFailService(): FailService {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideDetailsRemote(): DetailsRemote {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideDetailsService(): DetailsService {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGameRemote(): GameRemote {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGameService(): GameService {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideStreamerRemote(): StreamerRemote {
        return mock()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideStreamerService(): StreamerService {
        return mock()
    }
}