package digital.sogood.livestreamfails.mobile.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
class FailViewModel(val title: String,
                    val streamer: String?,
                    val game: String?,
                    val points: Int,
                    val nsfw: Boolean,
                    val thumbnailUrl: String,
                    val detailsUrl: String)