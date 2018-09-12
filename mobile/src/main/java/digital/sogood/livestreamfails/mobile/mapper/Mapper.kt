package digital.sogood.livestreamfails.mobile.mapper

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
interface Mapper<out V, in D> {
    fun mapToViewModel(type: D): V
}