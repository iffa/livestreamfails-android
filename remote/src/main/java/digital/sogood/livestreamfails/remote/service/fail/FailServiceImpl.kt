package digital.sogood.livestreamfails.remote.service.fail

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

    /**
     * TODO: Allow changing query parameters
     */
    override fun getFails(page: Int?): Single<List<FailModel>> {
        return Single.fromCallable {
            val models = mutableListOf<FailModel>()
            val uri = URIBuilder(ENDPOINT)
                    .addParameter("loadPostMode", "standard")
                    .addParameter("loadPostNSFW", "1")
                    .addParameter("loadPostOrder", "hot")
                    .addParameter("loadPostPage", page.toString())
                    .addParameter("loadPostTimeFrame", "week")
                    .build().toURL()

            val request = Request.Builder().url(uri).get().build()
            val html = okHttpClient.newCall(request).execute().body()?.string()
            val doc = Jsoup.parse(html)

            doc.select("div.post-card")?.let {
                it.forEach { card ->
                    val title = card.selectFirst("p.title").text()

                    val thumbnailUrl = card.selectFirst("img.card-img-top").attr("src")

                    val streamer = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(0)?.text()
                    val game = card.selectFirst("div.stream-info > small.text-muted")
                            ?.select("a[href]")?.get(1)?.text()

                    val nsfw = card.selectFirst("span.oi-warning") != null

                    val pointsElement = card.selectFirst("small.text-muted > span.oi-arrow-circle-top").parent()

                    val points = pointsElement
                            .ownText().replace(Regex("[^\\d.]"), "").toInt()

                    models.add(FailModel(title, streamer, game, points, nsfw, thumbnailUrl))
                }
            }

            models.toList()
        }
    }
}