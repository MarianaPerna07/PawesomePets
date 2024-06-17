package pt.ua.deti.icm.pawesomepets.database

import androidx.room.Database
import androidx.room.RoomDatabase

import pt.ua.deti.icm.pawesomepets.database.dao.DogDao
import pt.ua.deti.icm.pawesomepets.database.entities.DogEntity

@Database(
    entities = [DogEntity::class],
    version = 1,
    exportSchema = true
)

abstract class PawesomePetsDatabase : RoomDatabase() {

    abstract fun dogDao(): DogDao

}