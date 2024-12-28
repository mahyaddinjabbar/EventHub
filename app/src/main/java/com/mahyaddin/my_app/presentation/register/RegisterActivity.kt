package com.mahyaddin.my_app.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.UserManager
import com.mahyaddin.my_app.presentation.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private val backButton: Button by lazy { findViewById(R.id.button_back_login) }
    private val registerButton: Button by lazy { findViewById(R.id.button_register) }
    private val emailEditText: EditText by lazy { findViewById(R.id.edit_text_email) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.edit_text_password) }
    private val nameEditText: EditText by lazy { findViewById(R.id.edit_text_name) }
    private val surnameEditText: EditText by lazy { findViewById(R.id.edit_text_surname) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        UserManager.start(this)

        backButton.setOnClickListener { finish() }

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()


            if (email.isNotEmpty()
                && password.isNotEmpty()
                && name.isNotEmpty()
                && surname.isNotEmpty()
            ) {
                if (!UserManager.isUserExists(email)) {
                    UserManager.addUser(email, password, name, surname)
                    Log.d("RegisterActivity", "User registered: Email: $email, Password: $password, Name: $name, Surname: $surname")
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    showToast("Registration successful!")
                } else {
                    // User already exists
                    showToast("User already exists with this email")
                }
            } else {
                // Empty fields
                showToast("Please fill in all fields")
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
