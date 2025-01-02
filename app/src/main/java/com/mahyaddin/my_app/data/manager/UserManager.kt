package com.mahyaddin.my_app.data.manager

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.mahyaddin.my_app.data.model.user.User

object UserManager {

    private var currentUser: User? = null
    private var sharedPreferences: SharedPreferences? = null

    fun start(context: Context) {
        sharedPreferences = context.getSharedPreferences("MySharedPref", MODE_PRIVATE)
    }

    // Method to add a new user
    fun addUser(email: String, password: String, name: String, surname: String) {



        sharedPreferences?.edit()?.apply {
            putString("email", email)
            putString("password", password)
            putString("name", name)
            putString("surname", surname)
            apply()
        }
    }

    // Method to check if a user with given email exists
    fun isUserExists(email: String): Boolean {
        val savedEmail = sharedPreferences?.getString("email", "")
        return savedEmail == email
    }

    // Method to verify user credentials and set the current user
    fun verifyUserCredentials(email: String, password: String): Boolean {
        sharedPreferences?.let { preferences ->
            val savedEmail = preferences.getString("email", "")
            val savedPassword = preferences.getString("password", "")
            val savedName = preferences.getString("name", "") ?: ""
            val savedSurname = preferences.getString("surname", "") ?: ""
            return if (savedEmail == email && savedPassword == password) {
                currentUser = User(savedEmail, savedPassword,"", savedName, savedSurname)
                true
            } else {
                false
            }
        }
        return false
    }

    // Method to get the current logged-in user
    fun getCurrentUser(): User? {
        return currentUser
    }

    // Method to log out the current user
    fun logout() {
        currentUser = null
    }
}
