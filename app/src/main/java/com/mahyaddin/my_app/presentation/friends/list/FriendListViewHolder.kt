package com.mahyaddin.my_app.presentation.friends.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.friend_request.FriendRequestStatus
import com.mahyaddin.my_app.data.model.friend_request.FriendRequestWrapper

class FriendListViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {

    private val fullname by lazy { view.findViewById<TextView>(R.id.textFullName) }
    private val email by lazy { view.findViewById<TextView>(R.id.textEmail) }
    private val addButton by lazy { view.findViewById<ImageView>(R.id.imageAddFriend) }
    private val acceptButton by lazy { view.findViewById<ImageView>(R.id.imageAccept) }
    private val rejectButton by lazy { view.findViewById<ImageView>(R.id.imageReject) }
    private val deleteButton by lazy { view.findViewById<ImageView>(R.id.imageDelete) }


    fun bind(
        friendRequest: FriendRequestWrapper,
        canBeDeleted: Boolean,
        addFriend: (FriendRequestWrapper) -> Unit,
        acceptRequest: (FriendRequestWrapper) -> Unit,
        rejectRequest: (FriendRequestWrapper) -> Unit,
        deleteUser: (FriendRequestWrapper) -> Unit
    ) {
        fullname.text = friendRequest.user.fullName()
        email.text = friendRequest.user.email
        showActions(canBeDeleted, friendRequest.requestStatus)
        when (friendRequest.requestStatus) {
            FriendRequestStatus.NOT_REQUESTED -> {
                addButton.setImageResource(R.drawable.ic_add)
            }

            FriendRequestStatus.SENT_PENDING -> {
                addButton.setImageResource(R.drawable.ic_time)
            }

            FriendRequestStatus.RECEIVED_PENDING -> {

            }

            FriendRequestStatus.ACCEPTED -> {
                addButton.setImageResource(R.drawable.ic_check)

            }

            FriendRequestStatus.REJECTED -> {
                addButton.setImageResource(R.drawable.ic_rejected)

            }
        }
        addButton.setOnClickListener {
            when (friendRequest.requestStatus) {
                FriendRequestStatus.NOT_REQUESTED -> {
                    addFriend(friendRequest)
                }

                else -> {
                    // nothing to do
                }
            }
        }

        acceptButton.setOnClickListener {
            acceptRequest(friendRequest)
        }

        rejectButton.setOnClickListener {
            rejectRequest(friendRequest)
        }

        deleteButton.setOnClickListener {
            deleteUser(friendRequest)
        }
    }

    private fun showActions(canBeDeleted: Boolean, status: FriendRequestStatus) {
        if (canBeDeleted) {
            addButton.isVisible = false
            acceptButton.isVisible = false
            rejectButton.isVisible = false
            deleteButton.isVisible = true
            return
        }
        when (status) {
            FriendRequestStatus.NOT_REQUESTED -> {
                addButton.isVisible = true
                acceptButton.isVisible = false
                rejectButton.isVisible = false
                deleteButton.isVisible = false
            }

            FriendRequestStatus.SENT_PENDING -> {
                addButton.isVisible = true
                acceptButton.isVisible = false
                rejectButton.isVisible = false
                deleteButton.isVisible = false
            }

            FriendRequestStatus.RECEIVED_PENDING -> {
                addButton.isVisible = false
                acceptButton.isVisible = true
                rejectButton.isVisible = true
                deleteButton.isVisible = false
            }

            FriendRequestStatus.ACCEPTED -> {
                addButton.isVisible = false
                acceptButton.isVisible = false
                rejectButton.isVisible = false
                deleteButton.isVisible = false
            }

            FriendRequestStatus.REJECTED -> {
                addButton.isVisible = true
                acceptButton.isVisible = false
                rejectButton.isVisible = false
                deleteButton.isVisible = false
            }
        }
    }
}