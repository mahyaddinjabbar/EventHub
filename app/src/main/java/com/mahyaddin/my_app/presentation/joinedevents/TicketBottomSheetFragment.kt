package com.mahyaddin.my_app.presentation.joinedevents

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.event.Event

class TicketBottomSheetFragment : BottomSheetDialogFragment(R.layout.bottom_sheet_ticket) {

    private var event: Event? = null

    companion object {
        fun promptDialog(
            manager: FragmentManager,
            event: Event
        ) {
            val dialog = TicketBottomSheetFragment()
            dialog.event = event
            dialog.show(manager, "even-ticket-dialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventName = view.findViewById<TextView>(R.id.eventTitle)
        val eventDescription = view.findViewById<TextView>(R.id.textEventDescription)
        val eventLocation = view.findViewById<TextView>(R.id.textEventLocation)
        val eventDate = view.findViewById<TextView>(R.id.textEventDate)
        val eventType = view.findViewById<TextView>(R.id.textEventType)
        val closeButton = view.findViewById<ImageView>(R.id.imageClose)

        event?.let {
            eventName.text = it.name
            eventDescription.text = it.description
            eventLocation.text = it.location
            eventDate.text = it.date
            eventType.text = it.type.name
        }

        closeButton.setOnClickListener { dismiss() }
    }
}