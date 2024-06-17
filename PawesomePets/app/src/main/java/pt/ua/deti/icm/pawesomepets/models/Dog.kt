package pt.ua.deti.icm.pawesomepets.models

import java.util.UUID

data class Dog(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val description: String? = null,
    val isDone: Boolean = false
)