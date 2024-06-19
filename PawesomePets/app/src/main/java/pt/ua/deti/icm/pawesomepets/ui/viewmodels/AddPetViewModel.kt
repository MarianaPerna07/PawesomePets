package pt.ua.deti.icm.pawesomepets.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import pt.ua.deti.icm.pawesomepets.data.room.models.Breed
import pt.ua.deti.icm.pawesomepets.data.room.models.Pet
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository
import java.util.*

class AddPetViewModel(private val repository: Repository) : ViewModel() {

    private val _petName = MutableStateFlow("")
    val petName: StateFlow<String> = _petName

    private val _petAge = MutableStateFlow("")
    val petAge: StateFlow<String> = _petAge

    private val _petBreed = MutableStateFlow<Breed?>(null)
    val petBreed: StateFlow<Breed?> = _petBreed

    private val _breeds = MutableStateFlow<List<Breed>>(emptyList())
    val breeds: StateFlow<List<Breed>> = _breeds

    private val _isAdopted = MutableStateFlow(false)
    val isAdopted: StateFlow<Boolean> = _isAdopted

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    init {
        loadBreeds()
    }

    private fun loadBreeds() {
        viewModelScope.launch {
            repository.breeds.collect { breedList ->
                _breeds.value = breedList
            }
        }
    }

    fun onPetNameChange(newName: String) {
        _petName.value = newName
    }

    fun onPetAgeChange(newAge: String) {
        _petAge.value = newAge
    }

    fun onPetBreedChange(newBreed: Breed) {
        _petBreed.value = newBreed
    }

    fun onIsAdoptedChange(newStatus: Boolean) {
        _isAdopted.value = newStatus
    }

    fun onDescriptionChange(newDescription: String) {
        _description.value = newDescription
    }

    fun addPet() {
        viewModelScope.launch {
            val newPet = Pet(
                petName = _petName.value,
                age = _petAge.value.toInt(),
                breedIdFK = _petBreed.value?.id ?: 0,
                listIdFK = 1,  // Placeholder
                dateAdded = Date(),
                isAdopted = _isAdopted.value
            )
            repository.insertPet(newPet)
        }
    }

    fun addBreed(breedName: String, species: String) {
        viewModelScope.launch {
            val newBreed = Breed(breedName = breedName, species = species)
            repository.insertBreed(newBreed)
            loadBreeds()
        }
    }
}
