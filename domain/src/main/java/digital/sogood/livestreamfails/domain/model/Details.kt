package digital.sogood.livestreamfails.domain.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
data class Details(val title: String,
                   val streamer: String?,
                   val game: String?,
                   val points: Int,
                   val nsfw: Boolean,
                   val videoUrl: String,
                   val sourceUrl: String?)