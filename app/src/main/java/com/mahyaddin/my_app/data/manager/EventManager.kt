package com.mahyaddin.my_app.data.manager

import android.util.Log
import com.mahyaddin.my_app.data.model.Event

object EventManager {
    private val eventList = mutableListOf<Event>()

    // Method to add a new event
    fun addEvent(name: String, description: String, location: String, amountOfPeople: Int, date: String) {
        val newEvent = Event(
            name=name,
            description = description,
            location = location,
            amountOfPeople = amountOfPeople,
            date = date,
            isJoined = false)
        eventList.add(newEvent)
    }

    // Method to get all events
    fun getAllEvents(): List<Event> {
        return eventList
    }
    //Method to find an event by name
    fun findEventByName(name: String): Event? {
        return eventList.find { it.name == name }
    }

    fun joinEvent(eventId:Int) {
        val event = eventList.find { it.id == eventId }
        if (event != null) {
            event.isJoined = true
        } else {
            Log.e("EventManager", "Event with ID $eventId not found.")
        }
    }


    fun getJoinedEvents(): List<Event> {
        return eventList.filter { event -> event.isJoined }
    }
}
