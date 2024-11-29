package com.mahyaddin.my_app

import kotlin.random.Random

data class Event(
    val id: Int = Random.nextInt(),
    val name: String,
    val description: String,
    val location: String,
    val amountOfPeople: Int,
    val date: String,
    var isJoined: Boolean

)
