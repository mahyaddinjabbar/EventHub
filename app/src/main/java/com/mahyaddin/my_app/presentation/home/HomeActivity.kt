package com.mahyaddin.my_app.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.data.model.event.Event
import com.mahyaddin.my_app.presentation.addevent.AddEventActivity
import com.mahyaddin.my_app.presentation.friends.FriendsActivity
import com.mahyaddin.my_app.presentation.home.list.EventListAdapter
import com.mahyaddin.my_app.presentation.joinedevents.JoinedEventsActivity
import com.mahyaddin.my_app.presentation.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    private val adapter by lazy {
        EventListAdapter { event ->
            DatabaseManager.joinEvent(event,
                onSuccess = {
                    Toast.makeText(this@HomeActivity, "Joined ${event.name}", Toast.LENGTH_SHORT).show()
                },
                onFailure = {
                    Toast.makeText(this@HomeActivity, getString(R.string.common_error_message), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerEvents) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView.adapter = adapter
        // Dynamically create views for each event and add them to the layout
        DatabaseManager.notJoinedEvents.observe(this) { events ->
            adapter.setData(events, false)
        }

        val createEventButton = findViewById<Button>(R.id.button_create_event)
        createEventButton.setOnClickListener {
            Log.d("HomeActivity", "Create event button clicked")
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        val logOutButton = findViewById<ImageView>(R.id.imageLogout)
        logOutButton.setOnClickListener {
            Log.d("HomeActivity", "Log out button clicked")
            DatabaseManager.logout(
                onSuccess = {
                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Clear activity stack
                    startActivity(intent)
                    finish()
                }
            )
        }

        val imageMyFriend = findViewById<ImageView>(R.id.imageMyFriends)
        imageMyFriend.setOnClickListener {
            val intent = Intent(this, FriendsActivity::class.java)
            startActivity(intent)
        }

        val joinedEventsButton = findViewById<Button>(R.id.button_joined_events)
        joinedEventsButton.setOnClickListener {
            Log.d("HomeActivity", "Joined events button clicked")
            val intent = Intent(this, JoinedEventsActivity::class.java)
            startActivity(intent)
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
                DatabaseManager.joinEvent(event,
                    onSuccess = {
                        Toast.makeText(this@HomeActivity, "Joined ${event.name}", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this@HomeActivity, context.getString(R.string.common_error_message), Toast.LENGTH_SHORT).show()
                    }
                )

            }
        }

        layout.addView(eventTextView)
        layout.addView(joinButton)
        cardView.addView(layout)
        return cardView
    }
}
