package digital.sogood.livestreamfails.domain.test.factory

/**
 * Factory class for data instances.
 *
 * @author Santeri Elo <me@santeri.xyz>
 */
class DataFactory {
    companion object Factory {
        fun randomUuid(): String {
            return java.util.UUID.randomUUID().toString()
        }
    }
}