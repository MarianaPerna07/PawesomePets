package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import pt.ua.deti.icm.pawesomepets.models.Dog
import pt.ua.deti.icm.pawesomepets.ui.screens.DogFormScreen
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.DogFormViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

const val dogFormRoute = "dogForm"
const val dogIdArgument = "dogId"

fun NavGraphBuilder.dogFormScreen(
    onPopBackStack: () -> Unit,
) {
    composable("$dogFormRoute?$dogIdArgument={$dogIdArgument}") {
        val dogId = navArgument(dogIdArgument) {
            nullable = true
        }
        val scope = rememberCoroutineScope()
        val viewModel = koinViewModel<DogFormViewModel>(
            parameters = { parametersOf(dogId) })
        val uiState by viewModel.uiState.collectAsState()
        DogFormScreen(
            uiState = uiState,
            onSaveClick = {
                scope.launch {
                    viewModel.save()
                    onPopBackStack()
                }
            },
            onDeleteClick = {
                scope.launch {
                    viewModel.delete()
                    onPopBackStack()
                }
            })
    }
}

fun NavHostController.navigateToNewDogForm() {
    navigate(dogFormRoute)
}

fun NavHostController.navigateToEditDogForm(dog: Dog) {
    navigate("$dogFormRoute?$dogIdArgument=${dog.id}")
}