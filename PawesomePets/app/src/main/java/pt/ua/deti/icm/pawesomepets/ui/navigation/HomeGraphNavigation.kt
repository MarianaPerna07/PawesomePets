package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.navigation
import pt.ua.deti.icm.pawesomepets.models.Dog

const val homeGraphRoute = "homeGraph"

fun NavGraphBuilder.homeGraph(
    onNavigateToNewDogForm: () -> Unit,
    onNavigateToEditDogForm: (Dog) -> Unit,
    onPopBackStack: () -> Unit,
    //onNavigateToLogin: () -> Unit
) {
    navigation(
        startDestination = dogsListRoute,
        route = homeGraphRoute
    ) {
        dogsListScreen(
            onNavigateToNewDogForm = onNavigateToNewDogForm,
            onNavigateToEditDogForm = onNavigateToEditDogForm
            //onNavigateToLogin = onNavigateToLogin
        )
        dogFormScreen(onPopBackStack = onPopBackStack)
    }
}

fun NavHostController.navigateToHomeGraph(
    navOptions: NavOptions? = null
) {
    navigate(homeGraphRoute, navOptions)
}