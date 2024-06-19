package pt.ua.deti.icm.pawesomepets.data.room.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pet_lists")
data class PetList(
    @ColumnInfo(name = "list_id")
    @PrimaryKey
    val id: Int,
    val name: String
)

@Entity(tableName = "pets")
data class Pet(
    @ColumnInfo(name = "pet_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val petName: String,
    val age: Int,
    val breedIdFK: Int,
    val listIdFK: Int,
    val dateAdded: Date,
    val isAdopted: Boolean
)

@Entity(tableName = "breeds")
data class Breed(
    @ColumnInfo(name = "breed_id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val breedName: String,
    val species: String
)
