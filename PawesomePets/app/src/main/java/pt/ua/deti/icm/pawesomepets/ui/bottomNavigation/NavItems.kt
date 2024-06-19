package pt.ua.deti.icm.pawesomepets.ui.bottomNavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

val listOfNavItems = listOf(
    NavItem(
        label = "Map",
        icon = Icons.Default.LocationOn,
        route = Screens.MapScreen.name
    ),
    NavItem(
        label = "Home",
        icon = Icons.Default.Home,
        route = Screens.HomeScreen.name
    ),
    NavItem(
        label = "Profile",
        icon = Icons.Default.Person,
        route = Screens.ProfileScreen.name
    )
)