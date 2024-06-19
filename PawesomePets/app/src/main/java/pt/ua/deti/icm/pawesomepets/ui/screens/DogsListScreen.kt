package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BackHand
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import pt.ua.deti.icm.pawesomepets.models.Dog
import pt.ua.deti.icm.pawesomepets.ui.states.DogsListUiState


import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem

import pt.ua.deti.icm.pawesomepets.R

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import pt.ua.deti.icm.pawesomepets.Graph.repository
import pt.ua.deti.icm.pawesomepets.ui.bottomNavigation.listOfNavItems

import pt.ua.deti.icm.pawesomepets.ui.bottomNavigation.Screens
import pt.ua.deti.icm.pawesomepets.ui.navigation.addPetScreen
import pt.ua.deti.icm.pawesomepets.ui.navigation.addPetScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.petScreen
import pt.ua.deti.icm.pawesomepets.ui.navigation.petScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeScreen
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeScreenRoute
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeGeneratorScreen
import pt.ua.deti.icm.pawesomepets.ui.navigation.qrCodeGeneratorScreenRoute

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun DogsListScreen(
    uiState: DogsListUiState,
    modifier: Modifier = Modifier,
    onNewDogClick: () -> Unit = {},
    onDogClick: (Dog) -> Unit = {},
    onExitToAppClick: () -> Unit = {},
) {

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text(text = "${uiState.user}") },
            actions = {
                var isSearchTextFieldEnabled by remember { mutableStateOf(false) }
                var text by remember { mutableStateOf("") }
                AnimatedVisibility(visible = isSearchTextFieldEnabled) {
                    Icon(
                        Icons.Filled.Close,
                        contentDescription = "ícone para fechar campo de texto de busca",
                        Modifier
                            .clip(CircleShape)
                            .clickable {
                                isSearchTextFieldEnabled = false
                                text = ""
                            }
                            .padding(8.dp),
                    )
                }
                BasicTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(
                        animateFloatAsState(targetValue = if (isSearchTextFieldEnabled) 1f else 0f).value
                    ),
                    decorationBox = { innerTextField ->
                        if (text.isEmpty()) {
                            Text(
                                text = "O que você busca?",
                                style = TextStyle(color = Color.Gray.copy(alpha = 0.5f), fontStyle = FontStyle.Italic, fontSize = 18.sp)
                            )
                        }
                        innerTextField()
                    },
                    textStyle = TextStyle.Default.copy(fontSize = 18.sp)
                )
                AnimatedVisibility(visible = !isSearchTextFieldEnabled) {
                    Row {
                        Icon(
                            Icons.Filled.Search,
                            contentDescription = "ícone de busca",
                            Modifier
                                .clip(CircleShape)
                                .clickable { isSearchTextFieldEnabled = true }
                                .padding(8.dp),
                        )
                        Icon(
                            Icons.Default.Logout,
                            contentDescription = "ícone sair do app",
                            Modifier
                                .clip(CircleShape)
                                .clickable { onExitToAppClick() }
                                .padding(8.dp),
                        )
                    }
                }
            })

        // AQUI QUERO O NOVO CÓDIGO

        val context = LocalContext.current

        Row(
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            NotificationButton(
                icon = Icons.Default.Notifications,
                contentDescription = "Notification",
                onClick = { createNotification(context) }
            )
        }

        AppNavigation()

        Box(modifier = Modifier.weight(1f)) {
            ExtendedFloatingActionButton(
                onClick = onNewDogClick,
                Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
                    .zIndex(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(Icons.Filled.BackHand, contentDescription = "Add new dog icon")
                    Text(text = "")
                }
            }
            LazyColumn(Modifier.fillMaxSize()) {
                items(uiState.dogs) { dog ->
                    var showDescription by remember { mutableStateOf(false) }
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .combinedClickable(
                                onClick = { showDescription = !showDescription },
                                onLongClick = { onDogClick(dog) }
                            )
                    ) {
                        Box(
                            Modifier
                                .padding(vertical = 16.dp, horizontal = 8.dp)
                                .size(30.dp)
                                .border(
                                    border = BorderStroke(2.dp, color = Color.Gray),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clip(shape = RoundedCornerShape(8.dp))
                                .clickable {
                                    Log.i("DogsListScreen", "$dog")
                                    uiState.onDogDoneChange(dog)
                                }) {
                        }
                        Column(
                            Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            Text(
                                text = dog.name, style = TextStyle.Default.copy(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                                overflow = TextOverflow.Ellipsis, maxLines = 2
                            )
                            dog.description?.let { description ->
                                AnimatedVisibility(visible = showDescription && description.isNotBlank()) {
                                    Text(
                                        text = description,
                                        style = TextStyle.Default.copy(fontSize = 24.sp),
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 3
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

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
            addPetScreen(repository = repository, onPetAdded = {
                navController.popBackStack()
            })
            petScreen()
            qrCodeScreen()
            qrCodeGeneratorScreen()
        }
    }

}

@Composable
fun NotificationButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Button(onClick = onClick) {
        Icon(
            icon,
            contentDescription = contentDescription,
            Modifier
                .clip(CircleShape)
                .padding(1.dp)
        )
    }
}


fun createNotification(context: Context) {
    val CHANNEL_ID = "1234"
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.notification_lx)
        .setContentTitle("What does one flea say to another flea?")
        .setContentText("Shall we walk or wait for the dog?")
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setAutoCancel(true)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "MYCHANNEL"
        val desc = "Channel_Desc"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = desc
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.notify(0, builder.build())
}

