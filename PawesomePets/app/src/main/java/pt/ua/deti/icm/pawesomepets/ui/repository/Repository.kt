package pt.ua.deti.icm.pawesomepets.ui.repository

import kotlinx.coroutines.flow.Flow
import pt.ua.deti.icm.pawesomepets.data.room.PetDao
import pt.ua.deti.icm.pawesomepets.data.room.ListDao
import pt.ua.deti.icm.pawesomepets.data.room.BreedDao
import pt.ua.deti.icm.pawesomepets.data.room.models.Pet
import pt.ua.deti.icm.pawesomepets.data.room.models.PetList
import pt.ua.deti.icm.pawesomepets.data.room.models.Breed

class Repository(
    private val listDao: ListDao,
    private val breedDao: BreedDao,
    private val petDao: PetDao,
) {
    val breeds: Flow<List<Breed>> = breedDao.getAllBreeds()
    val getPetsWithListAndBreed = listDao.getPetsWithBreedAndList()

    fun getPetWithBreedAndList(id: Int) = listDao
        .getPetWithBreedAndListFilteredById(id)

    fun getPetWithBreedAndListFilteredById(id: Int) =
        listDao.getPetsWithBreedAndListFilteredById(id)

    suspend fun insertList(petList: PetList) {
        listDao.insertPetList(petList)
    }

    suspend fun insertBreed(breed: Breed) {
        breedDao.insert(breed)
    }

    suspend fun insertPet(pet: Pet) {
        petDao.insert(pet)
    }

    suspend fun deletePet(pet: Pet) {
        petDao.delete(pet)
    }

    suspend fun updatePet(pet: Pet) {
        petDao.update(pet)
    }
}
