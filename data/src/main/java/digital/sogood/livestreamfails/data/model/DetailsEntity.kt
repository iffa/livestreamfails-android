package digital.sogood.livestreamfails.data.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
data class DetailsEntity(
        val title: String,
        val streamer: String?,
        val game: String?,
        val points: Int,
        val nsfw: Boolean,
        val videoUrl: String,
        val sourceUrl: String?
)