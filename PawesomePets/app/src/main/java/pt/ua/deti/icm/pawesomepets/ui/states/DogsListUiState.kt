package pt.ua.deti.icm.pawesomepets.ui.states

import pt.ua.deti.icm.pawesomepets.database.entities.UserEntity
import pt.ua.deti.icm.pawesomepets.models.Dog

data class DogsListUiState(
    val dogs: List<Dog> = emptyList(),
    val onDogDoneChange: (Dog) -> Unit = {},
    val user: String? = null,
    val userEntity: UserEntity? = null
)