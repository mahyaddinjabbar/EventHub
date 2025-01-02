package com.mahyaddin.my_app.presentation.home.list

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.event.Event

class EventListViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {

    private val eventName by lazy { view.findViewById<TextView>(R.id.textEventName) }
    private val eventDescription by lazy { view.findViewById<TextView>(R.id.textEventDescription) }
    private val eventdate by lazy { view.findViewById<TextView>(R.id.textEventDate) }
    private val eventPeople by lazy { view.findViewById<TextView>(R.id.textEventPeopleCount) }
    private val eventLocation by lazy { view.findViewById<TextView>(R.id.textEventLocation) }
    private val eventType by lazy { view.findViewById<TextView>(R.id.textEventType) }
    private val joinButton by lazy { view.findViewById<Button>(R.id.buttonJoin) }

    fun bind(item: Event, isJoined: Boolean, join: (Event) -> Unit) {
        eventName.text = item.name
        eventDescription.text = item.description
        eventdate.text = item.date
        eventPeople.text = item.amountOfPeople.toString()
        eventLocation.text = item.location
        eventType.text = item.type.name
        joinButton.isVisible = !isJoined
        joinButton.setOnClickListener { join(item) }
    }
}