package com.mahyaddin.my_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class JoinedEventsActivity : AppCompatActivity() {
    private lateinit var joinedEventListLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_events)

        joinedEventListLayout = findViewById(R.id.joined_event_list_layout)

        displayJoinedEvents()

        val backButton = findViewById<Button>(R.id.button_back_home)
        backButton.setOnClickListener {
            finish()
        }
    }

    private fun displayJoinedEvents() {
        joinedEventListLayout.removeAllViews() // Clear the current views

        // Get the current user
        val currentUser = UserManager.getCurrentUser()
        Log.d("JoinedEventsActivity", "Current user: $currentUser") // Add this line for debugging
        currentUser?.let { user ->
            // Get the events the user has joined
            val joinedEvents = EventManager.getJoinedEvents()
            Log.d("JoinedEventsActivity", "Joined events: $joinedEvents") // Add this line for debugging

            // Dynamically create TextViews for each joined event and add them to the layout
            for (event in joinedEvents) {
                val eventTextView = TextView(this).apply {
                    text = "${event.name}\n${event.description}\n${event.location}\n${event.amountOfPeople} people\n${event.date}\n"
                    textSize = 18f
                    setPadding(16, 16, 16, 16)
                }
                joinedEventListLayout.addView(eventTextView)
            }
        } ?: run {
            // Handle the case when there is no logged-in user
            val noUserTextView = TextView(this).apply {
                text = "No user logged in"
                textSize = 18f
                setPadding(16, 16, 16, 16)
            }
            joinedEventListLayout.addView(noUserTextView)
        }
    }

}
