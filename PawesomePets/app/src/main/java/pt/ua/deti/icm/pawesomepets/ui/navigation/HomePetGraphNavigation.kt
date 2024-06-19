package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

fun NavController.navigateToHomeGraph(navOptions: NavOptions? = null) {
    this.navigate("homeGraph", navOptions)
}

fun NavController.navigateToAuthGraph(navOptions: NavOptions? = null) {
    this.navigate("authGraph", navOptions)
}

fun NavController.navigateToNewDogForm(navOptions: NavOptions? = null) {
    this.navigate("add_pet", navOptions)
}

fun NavController.navigateToEditDogForm(dogId: String, navOptions: NavOptions? = null) {
    this.navigate("edit_dog/$dogId", navOptions)
}

fun NavController.navigateToSignIn(navOptions: NavOptions? = null) {
    this.navigate("signIn", navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    this.navigate("signUp", navOptions)
}