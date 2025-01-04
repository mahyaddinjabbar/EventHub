package com.mahyaddin.my_app.presentation.home.list

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.event.Event

class EventListViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {

    private val eventName by lazy { view.findViewById<TextView>(R.id.textEventName) }
    private val eventDescription by lazy { view.findViewById<TextView>(R.id.textEventDescription) }
    private val eventDate by lazy { view.findViewById<TextView>(R.id.textEventDate) }
    private val eventPeople by lazy { view.findViewById<TextView>(R.id.textEventPeopleCount) }
    private val eventLocation by lazy { view.findViewById<TextView>(R.id.textEventLocation) }
    private val eventType by lazy { view.findViewById<TextView>(R.id.textEventType) }
    private val joinButton by lazy { view.findViewById<Button>(R.id.buttonJoin) }
    private val imageDelete by lazy { view.findViewById<ImageView>(R.id.imageDelete) }
    private val imageEdit by lazy { view.findViewById<ImageView>(R.id.imageEdit) }

    fun bind(
        item: Event,
        isAdmin: Boolean,
        isJoined: Boolean,
        delete: (Event) -> Unit,
        edit: (Event) -> Unit,
        join: (Event) -> Unit
    ) {
        eventName.text = item.name
        eventDescription.text = item.description
        eventDate.text = item.date
        eventPeople.text = item.amountOfPeople.toString()
        eventLocation.text = item.location
        eventType.text = item.type.name
        when {
            isAdmin -> {
                joinButton.isVisible = false
                imageDelete.isVisible = true
                imageEdit.isVisible = true
            }

            isJoined -> {
                joinButton.isVisible = false
                imageDelete.isVisible = false
                imageEdit.isVisible = false
            }

            else -> {
                joinButton.isVisible = true
                imageDelete.isVisible = false
                imageEdit.isVisible = false
            }
        }
        imageDelete.setOnClickListener { delete(item) }
        imageEdit.setOnClickListener { edit(item) }
        joinButton.setOnClickListener { join(item) }
    }
}