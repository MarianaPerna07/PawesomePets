package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.filled.Password
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ua.deti.icm.pawesomepets.ui.theme.PawesomePetsTheme

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Spacer

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import pt.ua.deti.icm.pawesomepets.ui.states.SignInUiState
import pt.ua.deti.icm.pawesomepets.ui.theme.Typography


@Composable
fun SignInScreen(
    uiState: SignInUiState,
    modifier: Modifier = Modifier,
    onSignInClick: () -> Unit,
    onSignUpClick: () -> Unit,
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        val isError = uiState.error != null
        AnimatedVisibility(visible = isError) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.error)
            ) {
                val error = uiState.error ?: ""
                Text(
                    text = error,
                    Modifier
                        .padding(16.dp),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
        Column(
            Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                Icons.Default.Pets,
                contentDescription = "Logo Pawesome Pets",
                Modifier
                    .clip(CircleShape)
                    .size(124.dp)
                    .background(MaterialTheme.colorScheme.primary, CircleShape)
                    .padding(8.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Pawesome Pets", style = Typography.titleLarge, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.size(16.dp))
            val textFieldModifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
            OutlinedTextField(
                value = uiState.email,
                onValueChange = uiState.onEmailChange,
                textFieldModifier,
                shape = RoundedCornerShape(25),
                leadingIcon = {
                    Icon(
                        Icons.Default.Person,
                        contentDescription = "Logo do User"
                    )
                },
                label = {
                    Text("Email")
                },
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = uiState.onPasswordChange,
                textFieldModifier,
                shape = RoundedCornerShape(25),
                leadingIcon = {
                    Icon(
                        Icons.Filled.Password,
                        contentDescription = "Logo da Password"
                    )
                },
                label = {
                    Text("Password")
                },
                trailingIcon = {
                    val trailingIconModifier = Modifier
                        .clip(CircleShape)
                        .clickable {
                            uiState.onTogglePasswordVisibility()
                        }
                    when (uiState.isShowPassword) {
                        true -> {
                            Icon(
                                Icons.Default.Visibility,
                                contentDescription = "Logo Visible",
                                trailingIconModifier
                            )
                        }

                        else -> Icon(
                            Icons.Default.VisibilityOff,
                            contentDescription = "Logo Not Visible",
                            trailingIconModifier
                        )
                    }
                },
                visualTransformation = when (uiState.isShowPassword) {
                    false -> PasswordVisualTransformation()
                    true -> VisualTransformation.None
                }
            )
            Button(
                onClick = onSignInClick,
                Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
            ) {
                Text(text = "Sign In")
            }
            TextButton(
                onClick = onSignUpClick,
                Modifier
                    .fillMaxWidth(0.8f)
                    .padding(8.dp)
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
fun SignInScreenPreview() {
    PawesomePetsTheme {
        SignInScreen(
            uiState = SignInUiState(),
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true, name = "with error")
@Composable
fun SignInScreen1Preview() {
    PawesomePetsTheme {
        SignInScreen(
            uiState = SignInUiState(
                error = "Erro Login"
            ),
            onSignInClick = {},
            onSignUpClick = {}
        )
    }
}