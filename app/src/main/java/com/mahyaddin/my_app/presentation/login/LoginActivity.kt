package com.mahyaddin.my_app.presentation.login

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.data.manager.UserManager
import com.mahyaddin.my_app.presentation.home.HomeActivity
import com.mahyaddin.my_app.presentation.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private val loginButton by lazy { findViewById<Button>(R.id.login_button) }
    private val emailEditText by lazy { findViewById<EditText>(R.id.email) }
    private val passwordEditText by lazy { findViewById<EditText>(R.id.password) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        UserManager.start(this)
        Log.d("LoginActivity", "onCreate started")
        configureSpannedRegisterText()

        loginButton.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                DatabaseManager.login(email, password,
                    onSuccess = {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    },
                    onFailure = {
                        Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configureSpannedRegisterText() {
        val registerText: TextView = findViewById(R.id.register_text)
        val text = getString(R.string.register_text)

        val spannableString = SpannableString(text)
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(view: View) {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
        val start = text.indexOf("Register")
        val end = start + "Register".length

        spannableString.setSpan(clickableSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        registerText.text = spannableString
        registerText.movementMethod = LinkMovementMethod.getInstance()
    }
}
