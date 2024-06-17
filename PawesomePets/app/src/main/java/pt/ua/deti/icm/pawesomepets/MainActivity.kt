package pt.ua.deti.icm.pawesomepets

import pt.ua.deti.icm.pawesomepets.ui.navigation.splashScreen
import pt.ua.deti.icm.pawesomepets.ui.navigation.splashScreenRoute
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import pt.ua.deti.icm.pawesomepets.ui.theme.PawesomePetsTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import pt.ua.deti.icm.pawesomepets.ui.navigation.authGraph
import pt.ua.deti.icm.pawesomepets.ui.navigation.homeGraph
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToAuthGraph
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToEditDogForm
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToHomeGraph
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToNewDogForm
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToSignIn
import pt.ua.deti.icm.pawesomepets.ui.navigation.navigateToSignUp
import org.koin.androidx.compose.koinViewModel
import pt.ua.deti.icm.pawesomepets.ui.screens.DogsListScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.MapScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.SettingsScreen
import pt.ua.deti.icm.pawesomepets.ui.states.DogsListUiState
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AppState
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AppViewModel

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            PawesomePetsTheme {

                val navController = rememberNavController()
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state.collectAsState(initial = AppState())
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentParentRoute = currentBackStack?.destination?.parent?.route

                LaunchedEffect(appState) {
                    if (appState.isInitLoading) {
                        return@LaunchedEffect
                    }

                    appState.user?.let {
                        navController.navigateToHomeGraph(navOptions = navOptions {
                            currentParentRoute?.let { parentRoute ->
                                popUpTo(parentRoute) {
                                    inclusive = true
                                }
                            } ?: popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        })
                    } ?: navController.navigateToAuthGraph(navOptions {
                        currentParentRoute?.let { parentRoute ->
                            popUpTo(parentRoute) {
                                inclusive = true
                            }
                        } ?: popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                    })
                }
                    //appState.user?.let {
                    //    navController.navigateToHomeGraph(navOptions = navOptions {
                    //        popUpTo("splashscreen") {
                    //            inclusive = true
                    //        }
                    //    })
                    //} ?: navController.navigateToAuthGraph(navOptions {
                    //    popUpTo("splashscreen") {
                    //        inclusive = true
                    //    }
                    //})

                NavHost(
                    navController = navController,
                    startDestination = splashScreenRoute
                    //startDestination = "splashscreen"
                    //startDestination = authGraphRoute
                ) {
                    splashScreen()
                    //composable("splashscreen") {
                    //    Box(modifier = Modifier
                    //        .fillMaxSize()
                    //        .background(MaterialTheme.colorScheme.background)
                    //    ) {
                    //        CircularProgressIndicator(
                    //            modifier = Modifier.align(Alignment.Center)
                    //        )
                    //    }
                    //}
                    authGraph(
                        //onNavigateToHomeGraph = {
                        //    navController.navigateToHomeGraph(it)
                        //},
                        onNavigateToSignIn = {
                            navController.navigateToSignIn(it)
                        },
                        onNavigateToSignUp = {
                            navController.navigateToSignUp()
                        }
                    )
                    homeGraph(
                        onNavigateToNewDogForm = {
                            navController.navigateToNewDogForm()
                        }, onNavigateToEditDogForm = { dog ->
                            navController.navigateToEditDogForm(dog)
                        }, onPopBackStack = {
                            navController.popBackStack()
                        }
                        //onNavigateToLogin = {
                        //    navController.navigateToAuthGraph()
                        //}
                    )
                }
                // A surface container using the 'background' color from the theme
                //Surface(
                //    modifier = Modifier.fillMaxSize(),
                //    color = MaterialTheme.colorScheme.background
                //) {
                    //AppNavigation()
                    //AuthScreen(onEnterClick = {
                    //    Log.i("MainActivity","onCreate: $it ")
                    //)

                    //val navController = rememberNavController()
                    //NavHost(navController = navController, startDestination = "main/{user}") {
                    //    composable("main/{user}") { entry ->
                    //        entry.arguments?.getString("user")?.let { user ->
                    //            MainScreen(user = user)
                    //        } ?: LaunchedEffect(null) {
                    //            navController.navigate("signIn")
                    //        }
                    //    }
                    //    composable("signIn") {
                    //        SignInScreen(
                    //            onSignInClick = { user ->
                    //                navController.navigate("main/${user.username}")
                    //            },
                    //            onSignUpClick = {
                    //                navController.navigate("signUp")
                    //            }
                    //        )
                    //    }
                    //    composable("signUp") {
                    //        SignUpScreen(
                    //            onSignUpClick = {
                    //                navController.popBackStack()
                    //            }
                    //        )
                    //    }
                    //}
            }
        }
    }
}

