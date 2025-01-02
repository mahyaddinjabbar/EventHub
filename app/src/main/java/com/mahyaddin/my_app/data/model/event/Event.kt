package com.mahyaddin.my_app.data.model.event

import java.util.UUID

data class Event(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val description: String = "",
    val location: String = "",
    val amountOfPeople: Int = 0,
    val date: String = "",
    var joinedUserIds: MutableList<String> = mutableListOf(),
    var type: EventType = EventType.PUBLIC
) {

    fun isJoined(id: String): Boolean {
        return joinedUserIds.toSet().contains(id)
    }

}
