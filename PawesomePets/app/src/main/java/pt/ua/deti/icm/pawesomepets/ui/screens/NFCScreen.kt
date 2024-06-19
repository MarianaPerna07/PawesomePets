package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun NFCScreen(
    onWriteNfc: (String) -> Unit,
    readText: String? = null
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var inputText by rememberSaveable { mutableStateOf("") }
    var isInputTextError by rememberSaveable { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        readText?.let {
            Text("Message read from NFC Tag:\n$it")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputText,
            onValueChange = {
                inputText = it
                isInputTextError = inputText.isBlank()
            },
            label = { Text("Please enter text") },
            isError = isInputTextError,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (inputText.isBlank()) {
                    isInputTextError = true
                } else {
                    isInputTextError = false
                    scope.launch {
                        onWriteNfc(inputText)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Write to NFC Tag")
        }

        if (isInputTextError) {
            Text("Please enter a valid text", color = MaterialTheme.colorScheme.error)
        }
    }
}
