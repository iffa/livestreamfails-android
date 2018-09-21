package digital.sogood.livestreamfails.presentation.util

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    val idlingResource = SimpleCountingIdlingResource(RESOURCE)

    fun increment() {
        idlingResource.increment()
    }

    fun decrement() {
        idlingResource.decrement()
    }
}