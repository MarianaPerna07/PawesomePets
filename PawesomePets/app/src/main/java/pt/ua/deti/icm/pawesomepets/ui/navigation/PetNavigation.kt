package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.ui.screens.PetScreen

const val petScreenRoute = "petScreen"

fun NavGraphBuilder.petScreen() {
    composable(route = petScreenRoute) {
        PetScreen()
    }
}

fun NavHostController.navigateToPetScreen(
    navOptions: NavOptions? = null
) {
    navigate(petScreenRoute, navOptions)
}