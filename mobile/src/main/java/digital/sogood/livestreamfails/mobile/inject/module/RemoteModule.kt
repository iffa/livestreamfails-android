package digital.sogood.livestreamfails.mobile.inject.module

import dagger.Binds
import dagger.Module
import dagger.Provides
import digital.sogood.livestreamfails.BuildConfig
import digital.sogood.livestreamfails.data.repository.details.DetailsRemote
import digital.sogood.livestreamfails.data.repository.fail.FailRemote
import digital.sogood.livestreamfails.data.repository.game.GameRemote
import digital.sogood.livestreamfails.data.repository.streamer.StreamerRemote
import digital.sogood.livestreamfails.remote.service.ServiceFactory
import digital.sogood.livestreamfails.remote.service.details.DetailsRemoteImpl
import digital.sogood.livestreamfails.remote.service.details.DetailsService
import digital.sogood.livestreamfails.remote.service.fail.FailRemoteImpl
import digital.sogood.livestreamfails.remote.service.fail.FailService
import digital.sogood.livestreamfails.remote.service.game.GameRemoteImpl
import digital.sogood.livestreamfails.remote.service.game.GameService
import digital.sogood.livestreamfails.remote.service.streamer.StreamerRemoteImpl
import digital.sogood.livestreamfails.remote.service.streamer.StreamerService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

/**
 * Module that provides all dependencies from the remote layer.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
@Module
abstract class RemoteModule {
    @Module
    companion object {
        @Provides
        @JvmStatic
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
            return logging
        }

        @Provides
        @Singleton
        @JvmStatic
        fun provideHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
            return OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
        }

        @Provides
        @JvmStatic
        fun provideFailService(httpClient: OkHttpClient): FailService {
            return ServiceFactory.makeFailService(httpClient)
        }

        @Provides
        @JvmStatic
        fun provideDetailsService(httpClient: OkHttpClient): DetailsService {
            return ServiceFactory.makeDetailsService(httpClient)
        }

        @Provides
        @JvmStatic
        fun provideStreamerService(httpClient: OkHttpClient): StreamerService {
            return ServiceFactory.makeStreamerService(httpClient)
        }

        @Provides
        @JvmStatic
        fun provideGameService(httpClient: OkHttpClient): GameService {
            return ServiceFactory.makeGameService(httpClient)
        }
    }

    @Binds
    abstract fun bindFailRemote(failRemoteImpl: FailRemoteImpl): FailRemote

    @Binds
    abstract fun bindDetailsRemote(detailsRemoteImpl: DetailsRemoteImpl): DetailsRemote

    @Binds
    abstract fun bindStreamerRemote(streamerRemoteImpl: StreamerRemoteImpl): StreamerRemote

    @Binds
    abstract fun bindGameRemote(gameRemoteImpl: GameRemoteImpl): GameRemote
}