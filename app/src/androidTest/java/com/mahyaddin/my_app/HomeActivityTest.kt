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
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    // Test if the RecyclerView is displayed
    @Test
    fun test_isEventListVisible_onAppLaunch() {
        onView(withId(R.id.recyclerEvents)).check(matches(isDisplayed()))
    }

    // Test if the Create Event Button is displayed
    @Test
    fun test_isCreateEventButtonVisible_onAppLaunch() {
        onView(withId(R.id.button_create_event)).check(matches(isDisplayed()))
    }

    // Test if the Log Out Button is displayed
    @Test
    fun test_isLogOutButtonVisible_onAppLaunch() {
        onView(withId(R.id.imageLogout)).check(matches(isDisplayed()))
    }

    // Test if the My Friends Button is displayed
    @Test
    fun test_isMyFriendsButtonVisible_onAppLaunch() {
        onView(withId(R.id.imageMyFriends)).check(matches(isDisplayed()))
    }

    // Test if the Joined Events Button is displayed
    @Test
    fun test_isJoinedEventsButtonVisible_onAppLaunch() {
        onView(withId(R.id.button_joined_events)).check(matches(isDisplayed()))
    }

    // Test the Create Event Button click functionality
    @Test
    fun test_navCreateEventScreen() {
        onView(withId(R.id.button_create_event)).perform(click())
    }

    // Test the Log Out Button click functionality
    @Test
    fun test_navLoginScreen() {
        onView(withId(R.id.imageLogout)).perform(click())
    }

    // Test the My Friends Button click functionality
    @Test
    fun test_navFriendsScreen() {
        onView(withId(R.id.imageMyFriends)).perform(click())
    }

    // Test the Joined Events Button click functionality
    @Test
    fun test_navJoinedEventsScreen() {
        onView(withId(R.id.button_joined_events)).perform(click())
    }
}
```
This Kotlin test class uses the Espresso framework to perform UI tests on the `HomeActivity` of the Android application. It tests the visibility of the RecyclerView and the buttons on app launch. It also tests the navigation to other screens when the buttons are clicked.