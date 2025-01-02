package com.mahyaddin.my_app

import android.app.Application
import com.google.firebase.FirebaseApp
import com.mahyaddin.my_app.data.manager.DatabaseManager


class EventhubApp : Application() {

    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
        DatabaseManager.start()
    }

}