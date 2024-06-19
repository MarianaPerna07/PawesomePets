package pt.ua.deti.icm.pawesomepets.ui.bottomNavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

import pt.ua.deti.icm.pawesomepets.ui.screens.HomeScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.MapScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.ProfileScreen

import pt.ua.deti.icm.pawesomepets.ui.navigation.addPetScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.petScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeGeneratorScreenRoute

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    Scaffold (
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                
                listOfNavItems.forEach { navItem ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                                  navController.navigate(navItem.route) {
                                      popUpTo(navController.graph.findStartDestination().id) {
                                          saveState = true
                                      }
                                      launchSingleTop = true
                                      restoreState = true
                                  }
                        },
                        icon = {
                               Icon(
                                   imageVector = navItem.icon,
                                   contentDescription = null
                               )
                        },
                        label = {
                            Text(text = navItem.label)

                        }

                    )
                }
            }
            
        }
    ){ paddingValues: PaddingValues ->
        NavHost(
            navController = navController,
            startDestination = Screens.HomeScreen.name,
            modifier = Modifier
                .padding(paddingValues)
        ) {
            composable(route = Screens.HomeScreen.name ) {
                HomeScreen(
                    onNewPetClick = {
                        navController.navigate(addPetScreenRoute)
                    },
                    onPetClick = {
                        navController.navigate(petScreenRoute)
                    },
                    onQRCodeClick = {
                        navController.navigate(qrCodeScreenRoute)
                    },
                    onGenerateQRCodeClick = {
                        navController.navigate(qrCodeGeneratorScreenRoute)
                    }
                )
            }
            composable(route = Screens.ProfileScreen.name ) {
                ProfileScreen()
            }
            composable(route = Screens.MapScreen.name ) {
                MapScreen(context = context)
            }
        }
    }

}
