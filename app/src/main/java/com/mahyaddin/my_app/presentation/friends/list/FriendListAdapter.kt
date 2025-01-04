package com.mahyaddin.my_app.presentation.friends.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.friend_request.FriendRequestWrapper

class FriendListAdapter(
    private val addFriend: (FriendRequestWrapper) -> Unit,
    private val acceptRequest: (FriendRequestWrapper) -> Unit,
    private val rejectRequest: (FriendRequestWrapper) -> Unit,
    private val deleteUser: (FriendRequestWrapper) -> Unit
) : RecyclerView.Adapter<FriendListViewHolder>() {

    private val items = mutableListOf<FriendRequestWrapper>()
    private var canBeDeleted: Boolean = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return FriendListViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(items[position], canBeDeleted, addFriend, acceptRequest, rejectRequest, deleteUser)
    }

    fun setData(data: List<FriendRequestWrapper>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    fun setCanBeDeleted(canBeDeleted: Boolean) {
        this.canBeDeleted = canBeDeleted
    }
}