package pt.ua.deti.icm.pawesomepets.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pt.ua.deti.icm.pawesomepets.data.room.models.Pet
import pt.ua.deti.icm.pawesomepets.data.room.models.PetList
import pt.ua.deti.icm.pawesomepets.data.room.models.Breed
import pt.ua.deti.icm.pawesomepets.data.room.converters.DateConverter

@TypeConverters(value = [DateConverter::class])
@Database(
    entities = [PetList::class,Pet::class,Breed::class],
    version = 1,
    exportSchema = false
)
abstract class PawesomePetsDB:RoomDatabase() {
    abstract fun listDao():ListDao
    abstract fun petDao():PetDao
    abstract fun breedDao():BreedDao

    companion object{
        @Volatile
        var INSTANCE:PawesomePetsDB? = null
        fun getDatabase(context:Context):PawesomePetsDB{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context,
                    PawesomePetsDB::class.java,
                    "pawesome.db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }

}