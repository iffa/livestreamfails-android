package digital.sogood.livestreamfails.presentation.mapper

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface Mapper<out V, in D> {
    fun mapToView(type: D): V
}