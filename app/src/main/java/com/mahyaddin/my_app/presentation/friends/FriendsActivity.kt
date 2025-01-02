package com.mahyaddin.my_app.presentation.friends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.presentation.friends.list.FriendListAdapter

class FriendsActivity : AppCompatActivity() {

    private val adapter by lazy {
        FriendListAdapter {
            DatabaseManager.requestFriend(it)
        }
    }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerUsers) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        recyclerView.adapter = adapter
        DatabaseManager.otherUsers.observe(this) { users ->
            users?.let { adapter.setData(it) }
        }
    }
}