package com.mahyaddin.my_app.data.manager

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mahyaddin.my_app.data.model.event.Event
import com.mahyaddin.my_app.data.model.event.EventType
import com.mahyaddin.my_app.data.model.friend_request.FriendRequest
import com.mahyaddin.my_app.data.model.friend_request.FriendRequestStatus
import com.mahyaddin.my_app.data.model.friend_request.FriendRequestWrapper
import com.mahyaddin.my_app.data.model.friend_request.RequestStatus
import com.mahyaddin.my_app.data.model.user.User
import com.mahyaddin.my_app.tools.CombinedLiveData

object DatabaseManager {

    private val database by lazy { FirebaseDatabase.getInstance() }
    private val userRef by lazy { database.getReference("user") }
    private val eventRef by lazy { database.getReference("event") }
    private val friendRequestRef by lazy { database.getReference("friendRequest") }

    private val currentUser = MutableLiveData<User>()
    val isAdmin = currentUser.map { it.isAdmin() }

    val allEvents = MutableLiveData<List<Event>>()
    private val allUsers = MutableLiveData<List<User>>()
    private val allFriendRequest = MutableLiveData<List<FriendRequest>>()

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


    private val otherUsers = CombinedLiveData<User, List<User>, List<User>?>(currentUser, allUsers) { user, allUsers ->
        return@CombinedLiveData when {
            user == null -> null
            allUsers == null -> null
            else -> {
                allUsers.filter { it.email != user.email }
            }
        }
    }

    private val usersByRequestStatus =
        CombinedLiveData<List<User>?, List<FriendRequest>, List<FriendRequestWrapper>?>(otherUsers, allFriendRequest) { users, requests ->
            val currentUser = currentUser.value ?: return@CombinedLiveData null
            return@CombinedLiveData when {
                users.isNullOrEmpty() -> emptyList()
                requests.isNullOrEmpty() -> users.map { FriendRequestWrapper(it) }
                else -> {
                    users.map { user ->
                        val status = requests.find { it.id == currentUser.id }
                        status
                            ?.let { FriendRequestWrapper(user, it.getStatusById(user.id)) }
                            ?: run { FriendRequestWrapper(user) }
                    }
                }
            }
        }

    private val showMyFriends = MutableLiveData(false)

    val usersByFriendGroup = CombinedLiveData<Boolean, List<FriendRequestWrapper>?, List<FriendRequestWrapper>?>(
        showMyFriends,
        usersByRequestStatus
    ) { showMyFriends, users ->
        return@CombinedLiveData when {
            users == null -> emptyList()
            showMyFriends == null -> users
            else -> {
                if (showMyFriends) {
                    users.filter { it.requestStatus == FriendRequestStatus.ACCEPTED }
                } else {
                    users
                }
            }
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

        friendRequestRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                saveFriendRequestList(snapshot)
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
        allEvents.postValue(eventList)

        // Use the event List (e.g., display in a RecyclerView)
        Log.d("Firebase", "Event list: $eventList")
    }

    private fun saveFriendRequestList(snapshot: DataSnapshot) {
        // Create a list to store user data
        val requestList = mutableListOf<FriendRequest>()
        for (eventSnapshot in snapshot.children) {
            // Map each child node to a User object
            val request = eventSnapshot.getValue(FriendRequest::class.java)
            request?.let { requestList.add(it) }
        }
        allFriendRequest.postValue(requestList)

        // Use the event List (e.g., display in a RecyclerView)
        Log.d("Firebase", "Request list: $requestList")
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

        if (isAdmin.value == true) return

        if (event.type == EventType.PRIVATE) {
            joinMeAndMyFriendsAutomatically(event)
        }
    }

    private fun joinMeAndMyFriendsAutomatically(event: Event) {

        // join the event for the current user
        val currentUserId = currentUser.value?.id ?: return
        event.joinedUserIds.add(currentUserId)

        // join the event for the friends of current user
        val allUsers = allUsers.value ?: emptyList()
        val friendRequests = allFriendRequest.value ?: emptyList()
        val requestForCurrentUser = friendRequests.find { it.id == currentUserId } ?: null

        val userIdsWhoseIsMyFriend = mutableSetOf<String>()

        // find the user ids whose I sent the friend request and ACCEPTED
        requestForCurrentUser?.sent?.forEach { (id, requestStatus) ->
            if (requestStatus.status == FriendRequestStatus.ACCEPTED) {
                userIdsWhoseIsMyFriend.add(id)
            }
        }
        // 2 3 5
        // find the user ids whose I received the friend request and ACCEPTED
        requestForCurrentUser?.received?.forEach { (id, requestStatus) ->
            if (requestStatus.status == FriendRequestStatus.ACCEPTED) {
                userIdsWhoseIsMyFriend.add(id)
            }
        }

        event.joinedUserIds.addAll(userIdsWhoseIsMyFriend.toList())

        updateEvent(event, {}, {})
    }

    fun deleteEvent(event: Event) {
        eventRef.child(event.id).removeValue()
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

    fun requestFriend(requestWrapper: FriendRequestWrapper) {
        val currentUser = currentUser.value ?: return
        val existRequests = allFriendRequest.value ?: emptyList()

        // save sent request
        val existSentObject = existRequests.find { it.id == currentUser.id } ?: FriendRequest(currentUser.id)
        val existSendMap = existSentObject.sent?.toMutableMap() ?: mutableMapOf()
        existSendMap[requestWrapper.user.id] = RequestStatus(FriendRequestStatus.SENT_PENDING)
        existSentObject.sent = existSendMap.toMap()
        friendRequestRef.child(currentUser.id).setValue(existSentObject)

        // save received request
        val existReceivedObject = existRequests.find { it.id == requestWrapper.user.id } ?: FriendRequest(requestWrapper.user.id)
        val existReceivedMap = existReceivedObject.received?.toMutableMap() ?: mutableMapOf()
        existReceivedMap[currentUser.id] = RequestStatus(FriendRequestStatus.RECEIVED_PENDING)
        existReceivedObject.received = existReceivedMap
        friendRequestRef.child(requestWrapper.user.id).setValue(existReceivedObject)
    }

    fun acceptRequest(request: FriendRequestWrapper) {
        val currentUser = currentUser.value ?: return
        friendRequestRef
            .child(request.user.id)
            .child("sent")
            .child(currentUser.id)
            .setValue(RequestStatus(FriendRequestStatus.ACCEPTED))

        friendRequestRef
            .child(currentUser.id)
            .child("received")
            .child(request.user.id)
            .setValue(RequestStatus(FriendRequestStatus.ACCEPTED))
    }

    fun rejectRequest(request: FriendRequestWrapper) {
        val currentUser = currentUser.value ?: return
        friendRequestRef
            .child(request.user.id)
            .child("sent")
            .child(currentUser.id)
            .setValue(RequestStatus(FriendRequestStatus.REJECTED))

        friendRequestRef
            .child(currentUser.id)
            .child("received")
            .child(request.user.id)
            .setValue(RequestStatus(FriendRequestStatus.REJECTED))
    }

    fun showMyFriends(show: Boolean) {
        this.showMyFriends.postValue(show)
    }

    fun deleteUser(user: User) {
        userRef.child(user.id).removeValue()
    }

}