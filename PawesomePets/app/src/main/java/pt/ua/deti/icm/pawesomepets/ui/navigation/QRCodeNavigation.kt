package pt.ua.deti.icm.pawesomepets.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.ui.screens.QRCodeScreen

const val qrCodeScreenRoute = "qrCodeScreen"

fun NavGraphBuilder.qrCodeScreen() {
    composable(route = qrCodeScreenRoute) {
        QRCodeScreen()
    }
}

fun NavHostController.navigateToQRCodeScreen(
    navOptions: NavOptions? = null
) {
    navigate(qrCodeScreenRoute, navOptions)
}