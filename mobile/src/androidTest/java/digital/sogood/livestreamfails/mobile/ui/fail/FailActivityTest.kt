package digital.sogood.livestreamfails.mobile.ui.fail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeUp
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.mobile.test.TestApplication
import digital.sogood.livestreamfails.mobile.test.factory.FailFactory
import digital.sogood.livestreamfails.mobile.test.util.RecyclerViewMatcher
import digital.sogood.livestreamfails.presentation.util.EspressoIdlingResource
import io.reactivex.Single
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class FailActivityTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule<FailActivity>(FailActivity::class.java, false, false)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun after() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun activityLaunches() {
        stubGetFails(Single.just(FailFactory.makeFailList(2)))

        activityRule.launchActivity(null)
    }

    @Test
    fun headerDisplays() {
        stubGetFails(Single.just(FailFactory.makeFailList(2)))
        activityRule.launchActivity(null)

        onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(hasDescendant(withText(R.string.tv_filter_timeframe))))
    }

    @Test
    fun failsDisplay() {
        val fails = FailFactory.makeFailList(2)
        stubGetFails(Single.just(fails))

        activityRule.launchActivity(null)

        checkDetailsDisplay(fails[0], 1)
    }

    @Test
    fun failsAreScrollable() {
        val fails = FailFactory.makeFailList(20)
        stubGetFailsFirstPageOnly(Single.just(fails))
        activityRule.launchActivity(null)

        fails.forEachIndexed { index, fail ->
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                    .scrollToPosition<RecyclerView.ViewHolder>(index + 1)) // Add one to index, [0] is header view
            checkDetailsDisplay(fail, index + 1)
        }
    }

    @Test
    fun noFailsDisplaysEmpty() {
        stubGetFails(Single.just(emptyList()))
        activityRule.launchActivity(null)

        onView(withId(R.id.recyclerView)).check(matches(withEffectiveVisibility(Visibility.GONE)))

        onView(withId(R.id.emptyListText)).check(matches(isDisplayed()))
    }

    @Test
    fun noMoreResultsDisplaysSnackbar() {
        val fails = FailFactory.makeFailList(20)
        stubGetFailsFirstPageOnly(Single.just(fails))
        activityRule.launchActivity(null)

        fails.forEachIndexed { index, _ ->
            onView(withId(R.id.recyclerView)).perform(RecyclerViewActions
                    .scrollToPosition<RecyclerView.ViewHolder>(index + 1)) // Add one to index, [0] is header view
        }

        onView(withText(R.string.no_more_results)).check(matches(isDisplayed()))
    }

    @Test
    fun scrollsToTopDisplaysAndScrolls() {
        stubGetFails(Single.just(FailFactory.makeFailList(10)))
        activityRule.launchActivity(null)

        onView(withId(R.id.rootLayout)).perform(swipeUp())

        onView(withId(R.id.scrollToTopFab)).check(matches(isDisplayed()))

        onView(withId(R.id.scrollToTopFab)).perform(click())

        onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerView).atPosition(0))
                .check(matches(isCompletelyDisplayed()))
    }

    private fun checkDetailsDisplay(fail: Fail, position: Int) {
        onView(RecyclerViewMatcher.withRecyclerView(R.id.recyclerView).atPosition(position))
                .check(matches(hasDescendant(withText(fail.title))))
    }

    private fun stubGetFails(single: Single<List<Fail>>) {
        whenever(TestApplication.appComponent().failRepository().getFails(
                any(), any(), any(), any(), any(), any()
        )).thenReturn(single)
    }

    private fun stubGetFailsFirstPageOnly(single: Single<List<Fail>>) {
        whenever(TestApplication.appComponent().failRepository().getFails(
                eq(0), any(), any(), any(), any(), any()
        )).thenReturn(single)

        whenever(TestApplication.appComponent().failRepository().getFails(
                eq(1), any(), any(), any(), any(), any()
        )).thenReturn(Single.just(emptyList()))
    }
}