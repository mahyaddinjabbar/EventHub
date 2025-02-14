package com.mahyaddin.my_app.presentation.register

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.data.manager.UserManager
import com.mahyaddin.my_app.data.model.user.User

class RegisterActivity : AppCompatActivity() {

    private val backButton: Button by lazy { findViewById(R.id.button_back_login) }
    private val registerButton: Button by lazy { findViewById(R.id.button_register) }
    private val emailEditText: EditText by lazy { findViewById(R.id.edit_text_email) }
    private val passwordEditText: EditText by lazy { findViewById(R.id.edit_text_password) }
    private val nameEditText: EditText by lazy { findViewById(R.id.edit_text_name) }
    private val surnameEditText: EditText by lazy { findViewById(R.id.edit_text_surname) }
    private val phoneEditText: EditText by lazy { findViewById(R.id.edit_text_phone) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        UserManager.start(this)

        backButton.setOnClickListener { finish() }

        registerButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()
            val phoneNumber = phoneEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            val isValidInputs = name.isNotEmpty()
                    && surname.isNotEmpty()
                    && phoneNumber.isNotEmpty()
                    && email.isNotEmpty()
                    && password.isNotEmpty()
                    && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$".toRegex()) // Email validation
                    && phoneNumber.matches("^\\+?[0-9]{10,13}$".toRegex()) // Phone validation
                    && password.length >= 8 // Password length validation
                    && password.matches(".*\\d.*".toRegex()) // Password numeric validation

            if (isValidInputs) {
                if (DatabaseManager.isExistUser(email)) {
                    showToast("User already exists with this email")
                } else {
                    val newUser = User(
                        name = name,
                        surname = surname,
                        phone = phoneNumber,
                        email = email,
                        password = password,
                    )
                    DatabaseManager.register(newUser,
                        onSuccess = {
                            showToast("Registration successful!")
                            finish()
                        },
                        onFailure = {
                            showToast("Something unusual happened! Please try again.")
                        }
                    )
                }
            } else {
                when {
                    name.isEmpty() || surname.isEmpty() || phoneNumber.isEmpty() || email.isEmpty() || password.isEmpty() -> {
                        showToast("Please fill in all fields")
                    }
                    !email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$".toRegex()) -> {
                        showToast("Invalid email format")
                    }
                    !phoneNumber.matches("^\\+?[0-9]{10,13}$".toRegex()) -> {
                        showToast("Invalid phone number format")
                    }
                    password.length < 8 || !password.matches(".*\\d.*".toRegex()) -> {
                        showToast("Password must be at least 8 characters long and include a number")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
