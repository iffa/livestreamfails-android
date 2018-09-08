package digital.sogood.livestreamfails.domain.model

data class Streamer(val name: String,
                    val fails: Int,
                    val imageUrl: String)