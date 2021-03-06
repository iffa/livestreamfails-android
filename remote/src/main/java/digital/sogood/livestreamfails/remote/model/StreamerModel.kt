package digital.sogood.livestreamfails.remote.model

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
data class StreamerModel(val name: String,
                         val fails: Int,
                         val imageUrl: String)