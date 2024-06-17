package pt.ua.deti.icm.pawesomepets.repositories


import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import pt.ua.deti.icm.pawesomepets.database.dao.DogDao
import pt.ua.deti.icm.pawesomepets.database.entities.DogEntity
import pt.ua.deti.icm.pawesomepets.models.Dog

class DogsRepository(
    private val dao: DogDao
) {

    val dogs get() = dao.findAll()

    suspend fun save(dog: Dog) = withContext(IO) {
        dao.save(dog.toDogEntity())
    }

    suspend fun delete(id: String) {
        dao.delete(
            DogEntity(id = id, name = "")
        )
    }

    fun findById(id: String) = dao.findById(id)

    suspend fun toggleIsDone(dog: Dog) = withContext(IO) {
    }

}

fun Dog.toDogEntity() = DogEntity(
    id = this.id,
    name = this.name,
    description = this.description,
)

fun DogEntity.toDog() = Dog(
    id = this.id,
    name = this.name,
    description = this.description,
)


