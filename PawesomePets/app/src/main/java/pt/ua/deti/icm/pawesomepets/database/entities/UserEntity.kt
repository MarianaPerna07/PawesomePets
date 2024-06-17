package pt.ua.deti.icm.pawesomepets.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(
    @PrimaryKey val id: String,
    val email: String,
    val imagePath: String? = null
)
