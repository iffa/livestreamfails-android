package digital.sogood.livestreamfails.remote.service

import digital.sogood.livestreamfails.remote.service.fail.FailService
import digital.sogood.livestreamfails.remote.service.fail.FailServiceImpl
import digital.sogood.livestreamfails.remote.service.game.GameService
import digital.sogood.livestreamfails.remote.service.game.GameServiceImpl
import digital.sogood.livestreamfails.remote.service.streamer.StreamerService
import digital.sogood.livestreamfails.remote.service.streamer.StreamerServiceImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [FailService], [GameService]
 * and [StreamerService], and their related dependencies.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
object ServiceFactory {
    fun makeFailService(isDebug: Boolean): FailService {
        val httpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug)
        )

        return makeFailService(httpClient)
    }

    fun makeGameService(isDebug: Boolean): GameService {
        val httpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug)
        )

        return makeGameService(httpClient)
    }

    fun makeStreamerService(isDebug: Boolean): StreamerService {
        val httpClient = makeOkHttpClient(
                makeLoggingInterceptor(isDebug)
        )

        return makeStreamerService(httpClient)
    }

    private fun makeFailService(okHttpClient: OkHttpClient): FailService {
        return FailServiceImpl(okHttpClient)
    }

    private fun makeGameService(okHttpClient: OkHttpClient): GameService {
        return GameServiceImpl(okHttpClient)
    }

    private fun makeStreamerService(okHttpClient: OkHttpClient): StreamerService {
        return StreamerServiceImpl(okHttpClient)
    }

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}