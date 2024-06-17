package pt.ua.deti.icm.pawesomepets.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pt.ua.deti.icm.pawesomepets.database.entities.DogEntity
import pt.ua.deti.icm.pawesomepets.models.Dog
import kotlinx.coroutines.flow.Flow

@Dao
interface DogDao {

    @Query("SELECT * FROM DogEntity")
    fun findAll(): Flow<List<DogEntity>>

    @Query("SELECT * FROM DogEntity WHERE id = :id")
    fun findById(id: String): Flow<DogEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(dog: DogEntity)

    @Delete
    suspend fun delete(dog: DogEntity)

}