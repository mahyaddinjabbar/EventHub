package com.mahyaddin.my_app.presentation.friends.list

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.model.user.User

class FriendListViewHolder(view: View) : RecyclerView.ViewHolder(view.rootView) {

    private val fullname by lazy { view.findViewById<TextView>(R.id.textFullName) }
    private val email by lazy { view.findViewById<TextView>(R.id.textEmail) }
    private val addButton by lazy { view.findViewById<ImageView>(R.id.imageAddFriend) }

    fun bind(user: User, addFriend: (User) -> Unit) {
        fullname.text = user.fullName()
        email.text = user.email
        addButton.setOnClickListener { addFriend(user) }
    }
}