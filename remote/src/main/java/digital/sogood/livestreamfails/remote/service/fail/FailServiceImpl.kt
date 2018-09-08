package digital.sogood.livestreamfails.remote.service.fail

import digital.sogood.livestreamfails.domain.model.Order
import digital.sogood.livestreamfails.domain.model.TimeFrame
import digital.sogood.livestreamfails.remote.model.FailModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.http.client.utils.URIBuilder
import org.jsoup.Jsoup

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class FailServiceImpl(private val okHttpClient: OkHttpClient) : FailService {
    companion object {
        private const val ENDPOINT = "https://livestreamfails.com/load/loadPosts.php"
    }

    override fun getFails(page: Int?, timeFrame: TimeFrame?, order: Order?,
                          nsfw: Boolean?, game: String?, streamer: String?)
            : Single<List<FailModel>> {
        return Single.fromCallable {
            val models = mutableListOf<FailModel>()

            var postMode = "standard"
            if (!streamer.isNullOrEmpty()) postMode = "streamer"
            if (!game.isNullOrEmpty()) postMode = "game"

            val nsfwParam = if (nsfw == true) 1 else 0

            val uri = URIBuilder(ENDPOINT)
                    .addParameter("loadPostMode", postMode)
                    .addParameter("loadPostNSFW", nsfwParam.toString())
                    .addParameter("loadPostOrder", order?.value ?: Order.HOT.value)
                    .addParameter("loadPostPage", page.toString())
                    .addParameter("loadPostTimeFrame", timeFrame?.value ?: TimeFrame.DAY.value)

            if (postMode == "streamer") uri.addParameter("loadPostModeStreamer", streamer)
            if (postMode == "game") uri.addParameter("loadPostModeGame", game)

            val request = Request.Builder().url(uri.build().toURL()).get().build()
            val html = okHttpClient.newCall(request).execute().body()?.string()
            val doc = Jsoup.parse(html)

            doc.select("div.post-card")?.let {
                it.forEach { card ->
                    val title = card.selectFirst("p.title").text()

                    val thumbnailUrl = card.selectFirst("img.card-img-top").attr("src")

                    val streamerName = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(0)?.text()
                    val gameName = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(1)?.text()

                    val isNsfw = card.selectFirst("span.oi-warning") != null

                    val pointsElement = card.selectFirst("small.text-muted > span.oi-arrow-circle-top").parent()

                    val points = pointsElement
                            .ownText().replace(Regex("[^\\d.]"), "").toInt()

                    models.add(FailModel(title, streamerName, gameName, points, isNsfw, thumbnailUrl))
                }
            }

            models.toList()
        }
    }
}
