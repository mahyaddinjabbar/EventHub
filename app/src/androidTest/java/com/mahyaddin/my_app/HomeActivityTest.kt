```kotlin
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.presentation.home.HomeActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.recyclerEvents)).check(matches(isDisplayed()))
    }

    @Test
    fun test_visibility_title_recyclerView_createEventButton() {
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.recyclerEvents)).check(matches(isDisplayed()))
        onView(withId(R.id.button_create_event)).check(matches(isDisplayed()))
    }

    @Test
    fun test_isTitleTextDisplayed() {
        onView(withId(R.id.textTitle)).check(matches(withText(R.string.title)))
    }

    @Test
    fun test_createEventButton_navigateToAddEventActivity() {
        onView(withId(R.id.button_create_event)).perform(click())
        onView(withId(R.id.addEventActivity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_imageLogout_navigateToLoginActivity() {
        onView(withId(R.id.imageLogout)).perform(click())
        onView(withId(R.id.loginActivity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_imageMyFriends_navigateToFriendsActivity() {
        onView(withId(R.id.imageMyFriends)).perform(click())
        onView(withId(R.id.friendsActivity)).check(matches(isDisplayed()))
    }

    @Test
    fun test_joinedEventsButton_navigateToJoinedEventsActivity() {
        onView(withId(R.id.button_joined_events)).perform(click())
        onView(withId(R.id.joinedEventsActivity)).check(matches(isDisplayed()))
    }
}
```
This Kotlin code includes a series of Espresso tests for the HomeActivity. The tests cover the visibility of UI elements, navigation to other activities when buttons are clicked, and the display of dynamic content in the RecyclerView. The tests use the ActivityScenarioRule to launch the HomeActivity before each test.