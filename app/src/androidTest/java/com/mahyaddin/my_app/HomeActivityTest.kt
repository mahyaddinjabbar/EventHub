```kotlin
// Required dependencies
// implementation 'androidx.test.espresso:espresso-core:3.4.0'
// implementation 'androidx.test.espresso:espresso-intents:3.4.0'
// implementation 'androidx.test:core:1.4.0'
// implementation 'androidx.test.ext:junit:1.1.3'
// implementation 'androidx.test:runner:1.4.0'
// implementation 'androidx.test:rules:1.4.0'

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mahyaddin.my_app.presentation.addevent.AddEventActivity
import com.mahyaddin.my_app.presentation.friends.FriendsActivity
import com.mahyaddin.my_app.presentation.home.HomeActivity
import com.mahyaddin.my_app.presentation.joinedevents.JoinedEventsActivity
import com.mahyaddin.my_app.presentation.login.LoginActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun testVisibility() {
        onView(withId(R.id.textTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.imageLogout)).check(matches(isDisplayed()))
        onView(withId(R.id.imageMyFriends)).check(matches(isDisplayed()))
        onView(withId(R.id.button_joined_events)).check(matches(isDisplayed()))
        onView(withId(R.id.button_create_event)).check(matches(isDisplayed()))
    }

    @Test
    fun testCreateEventButton() {
        onView(withId(R.id.button_create_event)).perform(click())
        Intents.intended(hasComponent(AddEventActivity::class.java.name))
    }

    @Test
    fun testLogoutButton() {
        onView(withId(R.id.imageLogout)).perform(click())
        Intents.intended(hasComponent(LoginActivity::class.java.name))
    }

    @Test
    fun testMyFriendsButton() {
        onView(withId(R.id.imageMyFriends)).perform(click())
        Intents.intended(hasComponent(FriendsActivity::class.java.name))
    }

    @Test
    fun testJoinedEventsButton() {
        onView(withId(R.id.button_joined_events)).perform(click())
        Intents.intended(hasComponent(JoinedEventsActivity::class.java.name))
    }

    // Add more tests as needed...
}
```
This test suite covers the basic UI interactions and navigation for the HomeActivity. Note that it does not cover all possible scenarios, such as error states or edge cases, as these would require additional context and setup.