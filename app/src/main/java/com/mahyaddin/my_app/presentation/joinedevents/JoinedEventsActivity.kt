package com.mahyaddin.my_app.presentation.joinedevents

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.presentation.home.list.EventListAdapter

class JoinedEventsActivity : AppCompatActivity() {

    private val adapter by lazy {
        EventListAdapter(
            delete = { DatabaseManager.deleteEvent(it) },
            edit = {},
            join = {},
            showTicket = {
                TicketBottomSheetFragment.promptDialog(supportFragmentManager, it)
            }
        )
    }

    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerEvents) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_joined_events)

        recyclerView.adapter = adapter
        // Dynamically create views for each event and add them to the layout
        DatabaseManager.joinedEvents.observe(this) { events -> adapter.setData(events, true) }

        DatabaseManager.isAdmin.observe(this) { adapter.setIsAdmin(it) }

        val backButton = findViewById<Button>(R.id.button_back_home)
        backButton.setOnClickListener { finish() }

    }
}
