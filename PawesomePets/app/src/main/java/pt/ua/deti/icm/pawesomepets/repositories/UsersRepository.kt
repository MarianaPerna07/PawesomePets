package pt.ua.deti.icm.pawesomepets.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import pt.ua.deti.icm.pawesomepets.database.dao.UserDao
import pt.ua.deti.icm.pawesomepets.database.entities.UserEntity

class UsersRepository(private val userDao: UserDao) {

    fun getUser(id: String): Flow<UserEntity?> = userDao.findById(id)

    suspend fun updateUser(user: UserEntity) = withContext(Dispatchers.IO) {
        userDao.updateUser(user)
    }
}