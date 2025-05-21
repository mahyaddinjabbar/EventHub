```kotlin
package com.mahyaddin.my_app.presentation.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahyaddin.my_app.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<HomeActivity> = ActivityScenarioRule(HomeActivity::class.java)

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

    // Test if the Logout Button is displayed
    @Test
    fun test_isLogoutButtonVisible_onAppLaunch() {
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

    // Test navigation to the Create Event Screen
    @Test
    fun test_navCreateEventScreen() {
        onView(withId(R.id.button_create_event)).perform(click())
        onView(withId(R.id.activity_create_event)).check(matches(isDisplayed()))
    }

    // Test navigation to the Login Screen
    @Test
    fun test_navLoginScreen() {
        onView(withId(R.id.imageLogout)).perform(click())
        onView(withId(R.id.activity_login)).check(matches(isDisplayed()))
    }

    // Test navigation to the Friends Screen
    @Test
    fun test_navFriendsScreen() {
        onView(withId(R.id.imageMyFriends)).perform(click())
        onView(withId(R.id.activity_friends)).check(matches(isDisplayed()))
    }

    // Test navigation to the Joined Events Screen
    @Test
    fun test_navJoinedEventsScreen() {
        onView(withId(R.id.button_joined_events)).perform(click())
        onView(withId(R.id.activity_joined_events)).check(matches(isDisplayed()))
    }
}
```

This Kotlin test class uses the Espresso framework to perform UI tests on the HomeActivity. It checks if all the necessary UI components are visible on app launch and tests the navigation to different screens when certain buttons are clicked. Note that the IDs for the destination activities in the navigation tests are placeholders and should be replaced with the actual IDs in your layout files.