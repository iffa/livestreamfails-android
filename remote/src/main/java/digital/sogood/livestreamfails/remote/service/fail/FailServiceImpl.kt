package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.remote.model.FailModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.http.client.utils.URIBuilder
import org.jsoup.Jsoup
import java.net.URL

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailServiceImpl(private val okHttpClient: OkHttpClient) : FailService {
    companion object {
        internal const val ENDPOINT = "https://livestreamfails.com/load/loadPosts.php"
    }

    override fun getFails(page: Int, timeFrame: TimeFrame, order: Order,
                          nsfw: Boolean, game: String, streamer: String)
            : Single<List<FailModel>> {
        return Single.fromCallable {
            val models = mutableListOf<FailModel>()

            val request = Request.Builder().url(getRequestUrl(page, timeFrame, order, nsfw, game, streamer)).get().build()
            val html = okHttpClient.newCall(request).execute().body()?.string()
            val doc = Jsoup.parse(html)

            doc.select("div.post-card")?.let {
                it.forEach { card ->
                    val title = card.selectFirst("p.title").text()

                    val detailsUrl = card.selectFirst("a[href]").attr("href")

                    val thumbnailUrl = card.selectFirst("img.card-img-top").attr("src")

                    val streamerName = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(0)?.text()
                    val gameName = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(1)?.text()

                    val isNsfw = card.selectFirst("span.oi-warning") != null

                    val pointsElement = card.selectFirst("small.text-muted > span.oi-arrow-circle-top").parent()

                    val points = pointsElement
                            .ownText().replace(Regex("[^\\d.]"), "").toInt()

                    models.add(FailModel(title, streamerName, gameName, points, isNsfw, thumbnailUrl, detailsUrl))
                }
            }

            models.toList()
        }
    }

    internal fun getRequestUrl(page: Int, timeFrame: TimeFrame, order: Order,
                               nsfw: Boolean, game: String, streamer: String): URL {
        val postMode = getPostMode(streamer, game)

        val builder = URIBuilder(ENDPOINT)
                .addParameter("loadPostMode", postMode)
                .addParameter("loadPostNSFW", (if (nsfw) 1 else 0).toString())
                .addParameter("loadPostOrder", order.value)
                .addParameter("loadPostPage", page.toString())
                .addParameter("loadPostTimeFrame", timeFrame.value)

        if (postMode == "streamer") builder.addParameter("loadPostModeStreamer", streamer)
        if (postMode == "game") builder.addParameter("loadPostModeGame", game)

        return builder.build().toURL()
    }

    internal fun getPostMode(streamer: String, game: String): String {
        if (!streamer.isEmpty()) return "streamer"
        if (!game.isEmpty()) return "game"
        return "standard"
    }
}
