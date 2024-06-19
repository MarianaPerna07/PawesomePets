package pt.ua.deti.icm.pawesomepets.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ua.deti.icm.pawesomepets.Graph
import pt.ua.deti.icm.pawesomepets.data.room.PetsWithBreedAndList
import pt.ua.deti.icm.pawesomepets.data.room.models.Pet
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import pt.ua.deti.icm.pawesomepets.utils.Category

class PetsListViewModel (
    private val repository: Repository = Graph.repository
):ViewModel() {

    var state by mutableStateOf(PetsListState())
        private set

    init {
        getPets()
    }

    private fun getPets(){
        viewModelScope.launch {
            repository.getPetsWithListAndBreed.collectLatest {
                Log.d("PetsListViewModel", "Pets collected: ${it.size}")
                state = state.copy(
                    pets = it
                )
            }
        }
    }

    fun deletePet(pet: Pet){
        viewModelScope.launch {
            repository.deletePet(pet)
        }
    }

    fun onCategoryChange(category: Category){
        state = state.copy(category = category)
        filterBy(category.id)
    }

    fun onPetAdoptedChange(pet: Pet, isAdopted:Boolean){
        viewModelScope.launch {

            repository.updatePet(
                pet = pet.copy(isAdopted = isAdopted)
            )

        }

    }

    private fun filterBy(petsListId:Int){
        if (petsListId != 10001){
            viewModelScope.launch {
                repository.getPetWithBreedAndListFilteredById(
                    petsListId
                ).collectLatest {
                    state = state.copy(pets = it)
                }
            }
        }else{
            getPets()
        }
    }

}

data class PetsListState(
    val pets:List<PetsWithBreedAndList> = emptyList(),
    val category: Category = Category(),
    val petAdopted:Boolean = true
)