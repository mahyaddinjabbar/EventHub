package com.mahyaddin.my_app.data.model.friend_request

import com.mahyaddin.my_app.data.model.user.User

data class FriendRequestWrapper(
    val user: User,
    val requestStatus: FriendRequestStatus = FriendRequestStatus.NOT_REQUESTED
)