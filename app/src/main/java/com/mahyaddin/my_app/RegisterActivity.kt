package com.mahyaddin.my_app
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        UserManager.start(this)
        val backButton = findViewById<Button>(R.id.button_back_login)
        backButton.setOnClickListener {
            finish()
        }

        val registerButton = findViewById<Button>(R.id.button_register)
        val emailEditText = findViewById<EditText>(R.id.edit_text_email)
        val passwordEditText = findViewById<EditText>(R.id.edit_text_password)
        val nameEditText = findViewById<EditText>(R.id.edit_text_name)
        val surnameEditText = findViewById<EditText>(R.id.edit_text_surname)

        registerButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val name = nameEditText.text.toString()
            val surname = surnameEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && surname.isNotEmpty()) {
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
