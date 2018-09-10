package digital.sogood.livestreamfails.remote.service

import digital.sogood.livestreamfails.remote.service.details.DetailsService
import digital.sogood.livestreamfails.remote.service.details.DetailsServiceImpl
import digital.sogood.livestreamfails.remote.service.fail.FailService
import digital.sogood.livestreamfails.remote.service.fail.FailServiceImpl
import digital.sogood.livestreamfails.remote.service.game.GameService
import digital.sogood.livestreamfails.remote.service.game.GameServiceImpl
import digital.sogood.livestreamfails.remote.service.streamer.StreamerService
import digital.sogood.livestreamfails.remote.service.streamer.StreamerServiceImpl
import okhttp3.OkHttpClient

/**
 * Provide "make" methods to create instances of [FailService], [GameService]
 * and [StreamerService], and their related dependencies.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
object ServiceFactory {
    fun makeFailService(okHttpClient: OkHttpClient): FailService {
        return FailServiceImpl(okHttpClient)
    }

    fun makeGameService(okHttpClient: OkHttpClient): GameService {
        return GameServiceImpl(okHttpClient)
    }

    fun makeStreamerService(okHttpClient: OkHttpClient): StreamerService {
        return StreamerServiceImpl(okHttpClient)
    }

    fun makeDetailsService(okHttpClient: OkHttpClient): DetailsService {
        return DetailsServiceImpl(okHttpClient)
    }
}