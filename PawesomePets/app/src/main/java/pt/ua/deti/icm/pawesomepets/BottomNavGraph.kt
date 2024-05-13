package pt.ua.deti.icm.pawesomepets

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pt.ua.deti.icm.pawesomepets.screens.HomeScreen
import pt.ua.deti.icm.pawesomepets.screens.ProfileScreen
import pt.ua.deti.icm.pawesomepets.screens.MapScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen()
        }
        composable(route = BottomBarScreen.Profile.route) {
            ProfileScreen()
        }
        composable(route = BottomBarScreen.Map.route) {
            MapScreen()
        }
    }
}