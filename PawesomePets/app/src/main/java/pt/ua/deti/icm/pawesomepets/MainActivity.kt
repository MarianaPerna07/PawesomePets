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
import pt.ua.deti.icm.pawesomepets.ui.screens.AddPetScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.DogsListScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.HomeScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.MapScreen
import pt.ua.deti.icm.pawesomepets.ui.states.DogsListUiState
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AppState
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AppViewModel
import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.*
import android.nfc.tech.Ndef
import android.os.Build
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import kotlin.experimental.and
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pt.ua.deti.icm.pawesomepets.ui.screens.LocationPermissionScreen
import pt.ua.deti.icm.pawesomepets.ui.screens.MapScreen
import pt.ua.deti.icm.pawesomepets.utils.checkForPermission

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {

    private var nfcAdapter: NfcAdapter? = null
    private var myTag: Tag? = null
    private var writeMode: Boolean = false
    private lateinit var pendingIntent: PendingIntent
    private lateinit var writeTagFilters: Array<IntentFilter>


    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (intent.action == NfcAdapter.ACTION_TAG_DISCOVERED) {
            val tag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG, Tag::class.java)
            } else {
                intent.getParcelableExtra(NfcAdapter.EXTRA_TAG)
            }
            tag?.id?.let {
                val tagValue = it.toHexString()
                val message = readNfcTag(intent)
                Toast.makeText(this, "NFC tag detected: $tagValue\nMessage: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        nfcAdapter?.let {
            val pendingIntent = PendingIntent.getActivity(
                this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
                PendingIntent.FLAG_IMMUTABLE
            )
            val intentFilters = createNFCIntentFilter()
            it.enableForegroundDispatch(this, pendingIntent, intentFilters, null)
        } ?: run {
            Log.e(TAG, "NFC is not available on this device.")
        }
    }

    override fun onPause() {
        super.onPause()
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Log.e(TAG, "NFC is not supported on this device.")
            Toast.makeText(this, "NFC is not supported on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            PendingIntent.FLAG_IMMUTABLE
        )
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        writeTagFilters = arrayOf(tagDetected)

        setContent {
            PawesomePetsTheme {
                val navController = rememberNavController()
                val appViewModel = koinViewModel<AppViewModel>()
                val appState by appViewModel.state.collectAsState(initial = AppState())
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentParentRoute = currentBackStack?.destination?.parent?.route

                var hasLocationPermission by remember {
                    mutableStateOf(checkForPermission(this))
                }

                if (hasLocationPermission) {
                    MapScreen(this)
                } else {
                    LocationPermissionScreen {
                        hasLocationPermission = true
                    }
                }

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

                NavHost(
                    navController = navController,
                    startDestination = splashScreenRoute
                ) {
                    splashScreen()
                    authGraph(
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
                    )
                }
            }
        }
    }
}

private fun readNfcTag(intent: Intent): String? {
    val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
    if (rawMessages != null) {
        val ndefMessages = rawMessages.map { it as NdefMessage }
        return ndefMessages.joinToString("\n") { message ->
            message.records.joinToString("\n") { record ->
                String(record.payload, Charset.forName("UTF-8")).trim { it <= ' ' }
            }
        }
    }
    return null
}


private fun createNFCIntentFilter(): Array<IntentFilter> {
    val intentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
    try {
        intentFilter.addDataType("*/*")
    } catch (e: IntentFilter.MalformedMimeTypeException) {
        throw RuntimeException("Failed to add MIME type.", e)
    }
    return arrayOf(intentFilter)
}

fun ByteArray.toHexString(): String {
    val hexChars = "0123456789ABCDEF"
    val result = StringBuilder(size * 2)

    map { byte ->
        val value = byte.toInt()
        val hexChar1 = hexChars[value shr 4 and 0x0F]
        val hexChar2 = hexChars[value and 0x0F]
        result.append(hexChar1)
        result.append(hexChar2)
    }

    return result.toString()
}


