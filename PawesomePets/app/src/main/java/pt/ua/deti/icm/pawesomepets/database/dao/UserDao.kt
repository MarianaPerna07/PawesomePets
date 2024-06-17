package pt.ua.deti.icm.pawesomepets.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pt.ua.deti.icm.pawesomepets.database.entities.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE id = :id")
    fun findById(id: String): Flow<UserEntity?>

    @Update
    suspend fun updateUser(user: UserEntity)
}