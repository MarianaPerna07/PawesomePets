package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.ui.screens.SignInScreen
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.SignInViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

const val signInRoute: String = "signIn"

fun NavGraphBuilder.signInScreen(
    //onNavigateToDogsList: () -> Unit,
    onNavigateToSignUp: () -> Unit
) {
    composable(signInRoute) {
        val viewModel = koinViewModel<SignInViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        val scope = rememberCoroutineScope()
        //val isAuthenticated by viewModel.isAuthenticated
        //    .collectAsState(initial = false)

        //LaunchedEffect(isAuthenticated) {
        //    if (isAuthenticated) {
        //        onNavigateToDogsList()
        //    }
        //}
        SignInScreen(
            uiState = uiState,
            onSignInClick = {
                scope.launch {
                    viewModel.signIn()
                }
            },
            onSignUpClick = onNavigateToSignUp
        )
    }

}

fun NavHostController.navigateToSignIn(
    navOptions: NavOptions? = null
) {
    navigate(signInRoute, navOptions)
}