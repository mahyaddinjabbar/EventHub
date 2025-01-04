package com.mahyaddin.my_app.data.model.friend_request

data class FriendRequest(
    val id: String? = null,
    var sent: Map<String, RequestStatus>? = null,
    var received: Map<String, RequestStatus>? = null
) {

    fun getStatusById(userId: String): FriendRequestStatus {
        // Check if the userId is in the received requests
        received?.get(userId)?.status?.let { return it }

        // Check if the userId is in the sent requests
        sent?.get(userId)?.status?.let { return it }

        // If not found in both, return NOT_REQUESTED
        return FriendRequestStatus.NOT_REQUESTED
    }
}

data class RequestStatus(
    val status: FriendRequestStatus? = null
)