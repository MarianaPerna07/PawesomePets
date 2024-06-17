package pt.ua.deti.icm.pawesomepets.ui.states

data class SignInUiState(
    val user: String = "",
    val email: String = "",
    val password: String = "",
    val onUserChange: (String) -> Unit = {},
    val onEmailChange: (String) -> Unit = {},
    val onPasswordChange: (String) -> Unit = {},
    val isShowPassword: Boolean = false,
    val onTogglePasswordVisibility: () -> Unit = {},
    val isAuthenticated: Boolean = false,
    val error: String? = null
)