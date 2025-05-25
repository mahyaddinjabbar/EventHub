package com.mahyaddin.my_app.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.presentation.addevent.AddEventActivity
import com.mahyaddin.my_app.presentation.friends.FriendsActivity
import com.mahyaddin.my_app.presentation.home.list.EventListAdapter
import com.mahyaddin.my_app.presentation.joinedevents.JoinedEventsActivity
import com.mahyaddin.my_app.presentation.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    companion object {
        const val EVENT_ID = "EVENT_ID"
    }

    private val adapter by lazy {
        EventListAdapter(
            delete = { event ->
                DatabaseManager.deleteEvent(event)
            },

            edit = {
                Log.d("HomeActivity", "Edit event button clicked")
                val intent = Intent(this, AddEventActivity::class.java)
                intent.putExtra(EVENT_ID,it.id)
                startActivity(intent)
            },
            join = { event ->
                DatabaseManager.joinEvent(event,
                    onSuccess = {
                        Toast.makeText(this@HomeActivity, "Joined ${event.name}", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(this@HomeActivity, getString(R.string.common_error_message), Toast.LENGTH_SHORT).show()
                    }
                )
            }
        )
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerEvents) }
    private val createEventButton by lazy { findViewById<Button>(R.id.button_create_event) }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView.adapter = adapter
        // Dynamically create views for each event and add them to the layout folder
        DatabaseManager.notJoinedEvents.observe(this) { events ->
            adapter.setData(events, false)
        }

        DatabaseManager.isAdmin.observe(this) { isAdmin ->
            adapter.setIsAdmin(isAdmin)
        }

        createEventButton.setOnClickListener {
            Log.d("HomeActivity", "Create event button clicked")
            val intent = Intent(this, AddEventActivity::class.java)
            startActivity(intent)
        }

        val logOutButton = findViewById<ImageView>(R.id.imageLogout)
        logOutButton.setOnClickListener {
            Log.d("HomeActivity", "the Log out button clicked")
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
}
