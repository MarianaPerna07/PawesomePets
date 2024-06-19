package pt.ua.deti.icm.pawesomepets.data.room

import androidx.room.*
import pt.ua.deti.icm.pawesomepets.data.room.models.Pet
import pt.ua.deti.icm.pawesomepets.data.room.models.PetList
import pt.ua.deti.icm.pawesomepets.data.room.models.Breed
import kotlinx.coroutines.flow.Flow

@Dao
interface PetDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pet: Pet)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(pet: Pet)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * FROM pets")
    fun getAllPets(): Flow<List<Pet>>

    @Query("SELECT * FROM pets WHERE pet_id =:petId")
    fun getPet(petId: Int): Flow<Pet>

}

@Dao
interface BreedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(breed: Breed)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(breed: Breed)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * FROM breeds")
    fun getAllBreeds(): Flow<List<Breed>>

    @Query("SELECT * FROM breeds WHERE breed_id =:breedId")
    fun getBreed(breedId: Int): Flow<Breed>
}

@Dao
interface ListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPetList(shoppingList: PetList)

    @Query("""
        SELECT * FROM pets AS I INNER JOIN pet_lists AS S
        ON I.listIdFK = S.list_id INNER JOIN breeds AS ST
        ON I.breedIdFK = ST.breed_id
    """)
    fun getPetsWithBreedAndList():Flow<List<PetsWithBreedAndList>>
    @Query("""
        SELECT * FROM pets AS I INNER JOIN pet_lists AS S
        ON I.listIdFK = S.list_id INNER JOIN breeds AS ST
        ON I.breedIdFK = ST.breed_id WHERE S.list_id =:listId
    """)
    fun getPetsWithBreedAndListFilteredById(listId:Int)
            :Flow<List<PetsWithBreedAndList>>

    @Query("""
        SELECT * FROM pets AS I INNER JOIN pet_lists AS S
        ON I.listIdFK = S.list_id INNER JOIN breeds AS ST
        ON I.breedIdFK = ST.breed_id WHERE I.pet_id =:petId
    """)
    fun getPetWithBreedAndListFilteredById(petId: Int)
            :Flow<PetsWithBreedAndList>
}

data class PetsWithBreedAndList(
    @Embedded val pet: Pet,
    @Embedded val petList: PetList,
    @Embedded val breed: Breed,
)