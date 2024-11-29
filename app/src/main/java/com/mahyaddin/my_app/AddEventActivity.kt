package com.mahyaddin.my_app

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddEventActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        Log.d("AddEventActivity", "onCreate started")

        val backButton = findViewById<Button>(R.id.button_back_home)
        backButton.setOnClickListener {
            finish()
        }

        val submitButton = findViewById<Button>(R.id.button_submit)
        val eventNameEditText = findViewById<EditText>(R.id.edit_text_event_name)
        val eventDescriptionEditText = findViewById<EditText>(R.id.edit_text_event_description)
        val eventLocationEditText = findViewById<EditText>(R.id.edit_text_event_location)
        val eventAmountOfPeopleEditText = findViewById<EditText>(R.id.edit_text_event_people)
        val eventDateEditText = findViewById<EditText>(R.id.edit_text_event_date)

        submitButton.setOnClickListener {
            val eventName = eventNameEditText.text.toString()
            val eventDescription = eventDescriptionEditText.text.toString()
            val eventLocation = eventLocationEditText.text.toString()
            val eventAmountOfPeople = eventAmountOfPeopleEditText.text.toString().toIntOrNull() ?: 0
            val eventDate = eventDateEditText.text.toString()

            if (eventName.isNotEmpty() && eventDescription.isNotEmpty() && eventLocation.isNotEmpty() && eventAmountOfPeople > 0 && eventDate.isNotEmpty()) {
                EventManager.addEvent(eventName, eventDescription, eventLocation, eventAmountOfPeople, eventDate)
                Log.d("AddEventActivity", "Event added: Name: $eventName, Description: $eventDescription, Location: $eventLocation, Amount of People: $eventAmountOfPeople, Date: $eventDate")
                Toast.makeText(this, "Event added successfully!", Toast.LENGTH_SHORT).show()
                finish() // Return to the previous activity
            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
