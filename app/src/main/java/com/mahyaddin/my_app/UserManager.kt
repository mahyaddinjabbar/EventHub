package com.mahyaddin.my_app

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences



data class User(val email: String, val password: String, val name: String, val surname: String)

object UserManager {
    private var currentUser: User? = null
    private var sharedPreferences: SharedPreferences? = null


    fun start(context: Context){
        sharedPreferences =
            context.getSharedPreferences("MySharedPref", MODE_PRIVATE)

    }

    // Method to add a new user
    fun addUser(email: String, password: String, name: String, surname: String) {
        with(sharedPreferences?.edit()){
            this?.putString("email", email)
            this?.putString("password", password)
            this?.putString("name", name)
            this?.putString("surname", surname)
            this?.apply()
        }
    }

    // Method to check if a user with given email exists
    fun isUserExists(email: String): Boolean {
        var savedEmail = sharedPreferences?.getString("email","")
        return savedEmail == email
    }

    // Method to verify user credentials and set the current user
    fun verifyUserCredentials(email: String, password: String): Boolean {
        sharedPreferences?.let { preferences ->
            var savedEmail = preferences.getString("email","")
            var savedPassword = preferences.getString("password","")
            var savedName = preferences.getString("name","")?:""
            var savedSurname = preferences.getString("surname","")?:""
            return if (savedEmail == email && savedPassword == password) {
                currentUser = User(savedEmail, savedPassword, savedName, savedSurname )
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
