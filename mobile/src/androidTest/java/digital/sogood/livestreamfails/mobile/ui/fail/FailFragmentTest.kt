package digital.sogood.livestreamfails.mobile.ui.fail

import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import digital.sogood.livestreamfails.R
import digital.sogood.livestreamfails.domain.model.Fail
import digital.sogood.livestreamfails.mobile.test.TestApplication
import digital.sogood.livestreamfails.mobile.test.factory.FailFactory
import digital.sogood.livestreamfails.mobile.ui.main.MainActivity
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals

/**
 * @author Santeri Elo <me@santeri.xyz>
 */
@RunWith(AndroidJUnit4::class)
class FailFragmentTest {
    @Rule
    @JvmField
    val activityRule = ActivityTestRule<MainActivity>(MainActivity::class.java, false, false)

    @Before
    fun setUp() {
        stubGetFails(Single.just(FailFactory.makeFailList(2)))
        activityRule.launchActivity(null)
    }

    private fun stubGetFails(single: Single<List<Fail>>) {
        whenever(TestApplication.appComponent().failRepository().getFails(any(),
                any(), any(), any(),
                any(), any()))
                .thenReturn(single)
    }

    @Test
    fun toolbarDisplaysCorrectTitle() {
        onView(withId(R.id.toolbar)).check { view, _ ->
            assertEquals(activityRule.activity.getString(R.string.app_name), (view as Toolbar).title)
        }
    }
}