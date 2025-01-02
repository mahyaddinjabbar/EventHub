package com.mahyaddin.my_app.presentation.friends.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.user.User

class FriendListAdapter(private val addFriend: (User) -> Unit) : RecyclerView.Adapter<FriendListViewHolder>() {

    private val items = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_list, parent, false)
        return FriendListViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FriendListViewHolder, position: Int) {
        holder.bind(items[position], addFriend)
    }

    fun setData(data: List<User>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }
}