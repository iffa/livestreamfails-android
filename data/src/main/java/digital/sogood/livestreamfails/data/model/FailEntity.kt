package digital.sogood.livestreamfails.data.model

data class FailEntity(val title: String,
                      val streamer: String?,
                      val game: String?,
                      val points: Int,
                      val nsfw: Boolean,
                      val thumbnailUrl: String,
                      val postId: Long)