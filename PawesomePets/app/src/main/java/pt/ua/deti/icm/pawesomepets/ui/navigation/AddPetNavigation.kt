package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.ui.screens.AddPetScreen
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository

const val addPetScreenRoute = "addPetScreen"

fun NavGraphBuilder.addPetScreen(
    repository: Repository,
    onPetAdded: () -> Unit
) {
    composable(route = addPetScreenRoute) {
        AddPetScreen(repository = repository, onPetAdded = onPetAdded)
    }
}

fun NavHostController.navigateToAddPetScreen(
    navOptions: NavOptions? = null
) {
    navigate(addPetScreenRoute, navOptions)
}
