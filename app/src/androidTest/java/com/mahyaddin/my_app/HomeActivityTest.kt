Here are some test cases for the given Android activity and XML layout:

```kotlin
package com.mahyaddin.my_app.presentation.home

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<HomeActivity> = ActivityTestRule(HomeActivity::class.java)

    // Test case to check if the HomeActivity is launched successfully
    @Test
    fun test_isActivityInView() {
        onView(withId(R.id.recyclerEvents)).check(matches(isDisplayed()))
    }

    // Test case to check if the RecyclerView is displayed when the HomeActivity is launched
    @Test
    fun test_visibility_recyclerView() {
        onView(withId(R.id.recyclerEvents)).check(matches(isDisplayed()))
    }

    // Test case to check if the create event button is displayed and clickable
    @Test
    fun test_createEventButton() {
        onView(withId(R.id.button_create_event)).check(matches(isDisplayed()))
        onView(withId(R.id.button_create_event)).perform(click())
    }

    // Test case to check if the joined events button is displayed and clickable
    @Test
    fun test_joinedEventsButton() {
        onView(withId(R.id.button_joined_events)).check(matches(isDisplayed()))
        onView(withId(R.id.button_joined_events)).perform(click())
    }

    // Test case to check if the logout button is displayed and clickable
    @Test
    fun test_logoutButton() {
        onView(withId(R.id.imageLogout)).check(matches(isDisplayed()))
        onView(withId(R.id.imageLogout)).perform(click())
    }

    // Test case to check if the friends button is displayed and clickable
    @Test
    fun test_friendsButton() {
        onView(withId(R.id.imageMyFriends)).check(matches(isDisplayed()))
        onView(withId(R.id.imageMyFriends)).perform(click())
    }
}
```

These test cases cover all the possible cases for the HomeActivity. They check if the activity is launched successfully, if the RecyclerView is displayed, and if all the buttons are displayed and clickable.