package digital.sogood.livestreamfails.remote.service.game

import digital.sogood.livestreamfails.remote.model.GameModel
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.http.client.utils.URIBuilder
import org.jsoup.Jsoup

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
open class GameServiceImpl(private val okHttpClient: OkHttpClient) : GameService {
    companion object {
        private const val ENDPOINT = "https://livestreamfails.com/load/loadGames.php"
    }

    override fun getGames(page: Int?): Single<List<GameModel>> {
        return Single.fromCallable {
            val models = mutableListOf<GameModel>()
            val uri = URIBuilder(ENDPOINT)
                    .addParameter("loadGameOrder", "amount")
                    .addParameter("loadGamePage", page.toString())
                    .build().toURL()

            val request = Request.Builder().url(uri).get().build()
            val html = okHttpClient.newCall(request).execute().body()?.string()
            val doc = Jsoup.parse(html)

            doc.select("div.post-card")?.let {
                it.forEach { card ->
                    val name = card.selectFirst("p.card-text.title").text()
                    val fails = card.selectFirst("small.text-muted")
                            .text().replace(Regex("[^\\d.]"), "").toInt()
                    val imageUrl = card.selectFirst("img.card-img-top").attr("src")

                    models.add(GameModel(name, fails, imageUrl))
                }
            }

            models.toList()
        }
    }
}