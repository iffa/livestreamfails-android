package digital.sogood.livestreamfails.presentation.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailView(val title: String,
               val streamer: String?,
               val game: String?,
               val points: Int,
               val nsfw: Boolean,
               val thumbnailUrl: String,
               val postId: Long)