package com.mahyaddin.my_app.presentation.friends

import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.presentation.friends.list.FriendListAdapter

class FriendsActivity : AppCompatActivity() {

    private val adapter by lazy {
        FriendListAdapter(
            addFriend = { DatabaseManager.requestFriend(it) },
            acceptRequest = { DatabaseManager.acceptRequest(it) },
            rejectRequest = { DatabaseManager.rejectRequest(it) },
            deleteUser = { DatabaseManager.deleteUser(it.user) }
        )
    }
    private val recyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerUsers) }
    private val backButton by lazy { findViewById<ImageView>(R.id.imageBack) }
    private val radioGroup by lazy { findViewById<RadioGroup>(R.id.radioGroupUserTypes) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)
        backButton.setOnClickListener { finish() }

        recyclerView.adapter = adapter
        DatabaseManager.usersByFriendGroup.observe(this) { users ->
            users?.let { adapter.setData(it) }
        }

        DatabaseManager.isAdmin.observe(this) { isAdmin ->
            adapter.setCanBeDeleted(isAdmin)
        }


        radioGroup.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.buttonUsers -> {
                    DatabaseManager.showMyFriends(false)
                }

                R.id.buttonFriends -> {
                    DatabaseManager.showMyFriends(true)
                }
            }
        }

    }
}