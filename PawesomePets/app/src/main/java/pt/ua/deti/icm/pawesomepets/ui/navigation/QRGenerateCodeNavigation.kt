package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.ui.screens.QRCodeGeneratorScreen

const val qrCodeGeneratorScreenRoute = "qrCodeGeneratorScreen"

fun NavGraphBuilder.qrCodeGeneratorScreen() {
    composable(route = qrCodeGeneratorScreenRoute) {
        QRCodeGeneratorScreen()
    }
}

fun NavHostController.navigateToQRCodeGeneratorScreen(
    navOptions: NavOptions? = null
) {
    navigate(petScreenRoute, navOptions)
}