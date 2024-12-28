package com.mahyaddin.my_app.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.UserManager
import com.mahyaddin.my_app.data.manager.EventManager
import com.mahyaddin.my_app.data.model.Event
import com.mahyaddin.my_app.presentation.addevent.AddEventActivity
import com.mahyaddin.my_app.presentation.joinedevents.JoinedEventsActivity
import com.mahyaddin.my_app.presentation.login.LoginActivity

class HomeActivity : AppCompatActivity() {
    private lateinit var eventListLayout: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        eventListLayout = findViewById(R.id.event_list_layout)

        val createEventButton = findViewById<Button>(R.id.button_create_event)
        createEventButton.setOnClickListener {
            Log.d("HomeActivity", "Create event button clicked")
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        val logOutButton = findViewById<Button>(R.id.button_logout)
        logOutButton.setOnClickListener {
            Log.d("HomeActivity", "Log out button clicked")
            UserManager.logout()  // Ensure current user is logged out
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clear activity stack
            startActivity(intent)
            finish()
        }

        val joinedEventsButton = findViewById<Button>(R.id.button_joined_events)
        joinedEventsButton.setOnClickListener {
            Log.d("HomeActivity", "Joined events button clicked")
            val intent = Intent(this, JoinedEventsActivity::class.java)
            startActivity(intent)
        }

        displayEvents()
    }

    override fun onResume() {
        super.onResume()
        displayEvents() // Refresh events list when the activity resumes
    }

    private fun displayEvents() {
        eventListLayout.removeAllViews() // Clear the current views

        // Get all events from the EventManager
        val events = EventManager.getAllEvents()

        // Dynamically create views for each event and add them to the layout
        for (event in events) {
            val eventCard = createEventCard(event)
            eventListLayout.addView(eventCard)
        }
    }

    private fun createEventCard(event: Event): CardView {
        val cardView = CardView(this).apply {
            radius = 16f
            setContentPadding(16, 16, 16, 16)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(16, 16, 16, 16)
            }
            cardElevation = 8f
        }

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
        }

        val eventTextView = TextView(this).apply {
            text = "${event.name}\n${event.description}\n${event.location}\n${event.amountOfPeople} people\n${event.date}\n"
            textSize = 18f
        }

        val joinButton = Button(this).apply {
            text = "Join"
            setOnClickListener {
                EventManager.joinEvent(event.id)
                Toast.makeText(this@HomeActivity, "Joined ${event.name}", Toast.LENGTH_SHORT).show()
            }
        }

        layout.addView(eventTextView)
        layout.addView(joinButton)
        cardView.addView(layout)
        return cardView
    }
}
