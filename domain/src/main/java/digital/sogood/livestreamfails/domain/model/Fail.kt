package digital.sogood.livestreamfails.domain.model

data class Fail(val title: String,
                val streamer: String?,
                val game: String?,
                val points: Int,
                val nsfw: Boolean,
                val thumbnailUrl: String)