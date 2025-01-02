package com.mahyaddin.my_app.data.model.user

import java.util.UUID

data class User(
    val id: String = UUID.randomUUID().toString(),
    val name: String = "",
    val surname: String = "",
    val email: String = "",
    val password: String = "",
    val phone: String = ""
) {
    fun fullName(): String {
        return "$name $surname"
    }
}