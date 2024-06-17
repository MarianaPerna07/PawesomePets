package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.models.Dog
import pt.ua.deti.icm.pawesomepets.ui.screens.DogsListScreen
import pt.ua.deti.icm.pawesomepets.ui.states.DogsListUiState
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.DogsListViewModel
import org.koin.androidx.compose.koinViewModel

const val dogsListRoute = "dogsList"

fun NavGraphBuilder.dogsListScreen(
    onNavigateToNewDogForm: () -> Unit,
    onNavigateToEditDogForm: (Dog) -> Unit,
    //onNavigateToLogin: () -> Unit
) {
    composable(dogsListRoute) {
        val viewModel = koinViewModel<DogsListViewModel>()
        val uiState by viewModel.uiState
            .collectAsState(DogsListUiState())
        DogsListScreen(
            uiState = uiState,
            onNewDogClick = onNavigateToNewDogForm,
            onDogClick = onNavigateToEditDogForm,
            onExitToAppClick = {
                viewModel.signOut()
                //onNavigateToLogin()
            }
        )
    }
}

fun NavHostController.navigateToDogsList() {
    navigate(dogsListRoute)
}