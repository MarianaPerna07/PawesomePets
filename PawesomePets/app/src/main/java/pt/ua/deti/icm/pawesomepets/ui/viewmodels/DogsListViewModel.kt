package pt.ua.deti.icm.pawesomepets.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ua.deti.icm.pawesomepets.repositories.DogsRepository
import pt.ua.deti.icm.pawesomepets.authentication.FirebaseAuthRepository
import pt.ua.deti.icm.pawesomepets.repositories.toDog
import pt.ua.deti.icm.pawesomepets.ui.states.DogsListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DogsListViewModel(
    private val repository: DogsRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DogsListUiState> =
        MutableStateFlow(DogsListUiState())

    val uiState
        get() = _uiState
            .combine(repository.dogs) { uiState, dogs ->
                uiState.copy(dogs = dogs.map { it.toDog() })
            }.combine(firebaseAuthRepository.currentUser) { uiState,authResult  ->
                uiState.copy(user = authResult.currentUser?.email)

            }

    init {
        viewModelScope.launch {
            _uiState.update { currentState ->
                currentState.copy(onDogDoneChange = { dog ->
                    viewModelScope.launch {
                        // esta linha é inútil
                        repository.toggleIsDone(dog)
                    }
                })
            }
        }
    }

    fun signOut() {
        firebaseAuthRepository.signOut()
    }

}