package pt.ua.deti.icm.pawesomepets.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import pt.ua.deti.icm.pawesomepets.models.Dog
import pt.ua.deti.icm.pawesomepets.repositories.DogsRepository
import pt.ua.deti.icm.pawesomepets.repositories.toDog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import pt.ua.deti.icm.pawesomepets.ui.states.DogFormUiState
import java.util.UUID

class DogFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val repository: DogsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<DogFormUiState> =
        MutableStateFlow(DogFormUiState())
    val uiState = _uiState.asStateFlow()
    private val id: String? = savedStateHandle["dogId"]

    init {
        _uiState.update { currentState ->
            currentState.copy(
                onNameChange = { name ->
                    _uiState.update {
                        it.copy(name = name)
                    }
                },
                onDescriptionChange = { description ->
                    _uiState.update {
                        it.copy(description = description)
                    }
                },
                topAppBarTitle = "Create Dog"
            )
        }
        id?.let {
            viewModelScope.launch {
                repository.findById(id)
                    .filterNotNull()
                    .mapNotNull {
                        it.toDog()
                    }.collectLatest { dog ->
                        _uiState.update { currentState ->
                            currentState.copy(
                                topAppBarTitle = "Edit Dog",
                                name = dog.name,
                                description = dog.description ?: "",
                                isDeleteEnabled = true
                            )
                        }
                    }
            }
        }
    }

    suspend fun save() {
        with(_uiState.value) {
            repository.save(
                Dog(
                    id = id ?: UUID.randomUUID().toString(),
                    name = name,
                    description = description
                )
            )
        }

    }

    suspend fun delete() {
        id?.let {
            repository.delete(id)
        }
    }

}