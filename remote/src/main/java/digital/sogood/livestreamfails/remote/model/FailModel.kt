package digital.sogood.livestreamfails.remote.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
data class FailModel(val title: String,
                     val streamer: String,
                     val game: String,
                     val points: Int,
                     val nsfw: Boolean,
                     val thumbnailUrl: String)