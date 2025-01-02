package com.mahyaddin.my_app.data.manager

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mahyaddin.my_app.data.model.event.Event
import com.mahyaddin.my_app.data.model.user.User
import com.mahyaddin.my_app.tools.CombinedLiveData

object DatabaseManager {

    private val database by lazy { FirebaseDatabase.getInstance() }
    private val userRef by lazy { database.getReference("user") }
    private val eventRef by lazy { database.getReference("event") }

    private val allUsers = MutableLiveData<List<User>>()

    private val _allEvents = MutableLiveData<List<Event>>()
    private val allEvents: LiveData<List<Event>>
        get() = _allEvents

    private val currentUser = MutableLiveData<User>()

    val otherUsers = CombinedLiveData<User, List<User>, List<User>?>(currentUser, allUsers) { user, allUsers ->
        return@CombinedLiveData when {
            user == null -> null
            allUsers == null -> null
            else -> {
                allUsers.filter { it.email != user.email }
            }
        }
    }


    val joinedEvents = CombinedLiveData<User, List<Event>, List<Event>>(currentUser, allEvents) { user, events ->
        return@CombinedLiveData when {
            events == null -> emptyList()
            user == null -> events
            else -> events.filter { it.isJoined(user.id) }
        }
    }

    val notJoinedEvents = CombinedLiveData<User, List<Event>, List<Event>>(currentUser, allEvents) { user, events ->
        return@CombinedLiveData when {
            events == null -> emptyList()
            user == null -> events
            else -> events.filter { it.isJoined(user.id).not() }
        }
    }

    fun start() {
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                saveUserList(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value", error.toException())
            }
        })
        eventRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                saveEventList(snapshot)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Failed to read value", error.toException())
            }
        })
    }

    private fun saveUserList(snapshot: DataSnapshot) {
        // Create a list to store user data
        val userList = mutableListOf<User>()
        for (userSnapshot in snapshot.children) {
            // Map each child node to a User object
            val user = userSnapshot.getValue(User::class.java)
            user?.let { userList.add(it) }
        }

        allUsers.postValue(userList)

        Log.d("Firebase", "User list: $userList")
    }

    private fun saveEventList(snapshot: DataSnapshot) {
        // Create a list to store user data
        val eventList = mutableListOf<Event>()
        for (eventSnapshot in snapshot.children) {
            // Map each child node to a User object
            val user = eventSnapshot.getValue(Event::class.java)
            user?.let { eventList.add(it) }
        }
        _allEvents.postValue(eventList)

        // Use the event List (e.g., display in a RecyclerView)
        Log.d("Firebase", "Event list: $eventList")
    }

    fun register(
        user: User,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {
        userRef.child(user.id).setValue(user)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun login(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onFailure: () -> Unit
    ) {

        val existUser = allUsers.value?.find { it.email == email && it.password == password }

        if (existUser != null) {
            currentUser.postValue(existUser)
            onSuccess()
        } else {
            onFailure()
        }
    }

    fun isExistUser(email: String): Boolean {
        val users = allUsers.value ?: return false
        val existEmails = users.map { it.email }.toSet()
        return existEmails.contains(email)
    }

    fun createEvent(event: Event, onSuccess: () -> Unit, onFailure: () -> Unit) {
        eventRef.child(event.id).setValue(event)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun joinEvent(event: Event, onSuccess: () -> Unit, onFailure: () -> Unit) {
        val currentUserId = currentUser.value?.id ?: return
        event.joinedUserIds.add(currentUserId)
        updateEvent(event, onSuccess, onFailure)
    }

    private fun updateEvent(event: Event, onSuccess: () -> Unit, onFailure: () -> Unit) {
        eventRef.child(event.id).setValue(event)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { onFailure() }
    }

    fun logout(onSuccess: () -> Unit) {
        currentUser.postValue(null)
        onSuccess()
    }

    fun requestFriend(it: User) {

    }

}