package com.mahyaddin.my_app.presentation.addevent

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mahyaddin.my_app.R
import com.mahyaddin.my_app.data.manager.DatabaseManager
import com.mahyaddin.my_app.data.model.event.Event
import com.mahyaddin.my_app.data.model.event.EventType
import com.mahyaddin.my_app.presentation.home.HomeActivity
import java.util.Calendar

class AddEventActivity : AppCompatActivity() {

    private val submitButton by lazy { findViewById<Button>(R.id.button_submit) }
    private val eventNameEditText by lazy { findViewById<EditText>(R.id.edit_text_event_name) }
    private val eventDescriptionEditText by lazy { findViewById<EditText>(R.id.edit_text_event_description) }
    private val eventLocationEditText by lazy { findViewById<EditText>(R.id.edit_text_event_location) }
    private val eventAmountOfPeopleEditText by lazy { findViewById<EditText>(R.id.edit_text_event_people) }
    private val eventDate by lazy { findViewById<TextView>(R.id.textDate) }
    private val radioGroup by lazy { findViewById<RadioGroup>(R.id.radioGroupEventTypes) }
    private val eventId by lazy { intent.getStringExtra(HomeActivity.EVENT_ID) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_event)
        Log.d("AddEventActivity", "onCreate started")

        DatabaseManager.allEvents.observe(this) { list ->
            val eventToBeEdited = list.find { it.id == eventId }
            if (eventToBeEdited != null) {
                eventNameEditText.setText(eventToBeEdited.name)
                eventDescriptionEditText.setText(eventToBeEdited.description)
                eventLocationEditText.setText(eventToBeEdited.location)
                eventAmountOfPeopleEditText.setText(eventToBeEdited.amountOfPeople.toString())
                eventDate.text = eventToBeEdited.date
                when (eventToBeEdited.type) {
                    EventType.PUBLIC -> radioGroup.check(R.id.buttonPublic)
                    EventType.PRIVATE -> radioGroup.check(R.id.buttonPrivate)
                }
            }
        }

        val backButton = findViewById<Button>(R.id.button_back_home)
        backButton.setOnClickListener { finish() }

        eventDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"
                eventDate.text = formattedDate
            }, year, month, day).show()
        }

        submitButton.setOnClickListener {
            val eventName = eventNameEditText.text.toString()
            val eventDescription = eventDescriptionEditText.text.toString()
            val eventLocation = eventLocationEditText.text.toString()
            val eventAmountOfPeople = eventAmountOfPeopleEditText.text.toString().toIntOrNull() ?: 0
            val eventDate = eventDate.text.toString()
            val eventType = when (radioGroup.checkedRadioButtonId) {
                R.id.buttonPublic -> EventType.PUBLIC
                R.id.buttonPrivate -> EventType.PRIVATE
                else -> EventType.PUBLIC
            }

            Log.e("LOG_NEW_EVENT: ", "EVENT TYPE-->$eventType")

            if (eventName.isNotEmpty()
                && eventDescription.isNotEmpty()
                && eventLocation.isNotEmpty()
                && eventAmountOfPeople > 0 &&
                eventDate.isNotEmpty()
            ) {
                val event = eventId?.let {
                    Event(
                        id = it,
                        name = eventName,
                        description = eventDescription,
                        location = eventLocation,
                        amountOfPeople = eventAmountOfPeople,
                        date = eventDate,
                        type = eventType
                    )
                } ?: run {
                    Event(
                        name = eventName,
                        description = eventDescription,
                        location = eventLocation,
                        amountOfPeople = eventAmountOfPeople,
                        date = eventDate,
                        type = eventType
                    )
                }

                DatabaseManager.createEvent(
                    event,
                    onSuccess = {
                        Log.d(
                            "AddEventActivity",
                            "Event Saved: Name: $eventName, Description: $eventDescription, Location: $eventLocation, Amount of People: $eventAmountOfPeople, Date: $eventDate"
                        )
                        Toast.makeText(this, "Event saved successfully!", Toast.LENGTH_SHORT).show()
                        finish() // Return to the previous activity
                    },
                    onFailure = {
                        Toast.makeText(this, "Something went wrong, please try again!", Toast.LENGTH_SHORT).show()
                    },
                )
            } else {
                Toast.makeText(this, "Please fill in all fields correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
