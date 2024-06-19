package pt.ua.deti.icm.pawesomepets.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository

class AddPetViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddPetViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddPetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
