package pt.ua.deti.icm.pawesomepets.ui.states

data class DogFormUiState(
    val name: String = "",
    val description: String = "",
    val topAppBarTitle: String = "",
    val onNameChange: (String) -> Unit = {},
    val onDescriptionChange: (String) -> Unit = {},
    val isDeleteEnabled: Boolean = false,
)