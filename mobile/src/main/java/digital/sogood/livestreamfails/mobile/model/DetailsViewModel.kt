package digital.sogood.livestreamfails.mobile.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
data class DetailsViewModel(val title: String,
                            val streamer: String?,
                            val game: String?,
                            val points: Int,
                            val nsfw: Boolean,
                            val videoUrl: String,
                            val sourceUrl: String?)