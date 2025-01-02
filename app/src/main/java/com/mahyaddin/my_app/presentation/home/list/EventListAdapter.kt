package com.mahyaddin.my_app.presentation.home.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.event.Event

class EventListAdapter(private val join: (Event) -> Unit) : RecyclerView.Adapter<EventListViewHolder>() {

    private val items = mutableListOf<Event>()
    private var isJoined = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_event_list, parent, false)
        return EventListViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        holder.bind(items[position], isJoined,join)
    }

    fun setData(events: List<Event>, isJoined: Boolean) {
        this.isJoined = isJoined
        items.clear()
        items.addAll(events)
        notifyDataSetChanged()
    }
}