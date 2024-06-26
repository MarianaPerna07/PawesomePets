package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pt.ua.deti.icm.pawesomepets.ui.states.SignUpUiState
import pt.ua.deti.icm.pawesomepets.ui.theme.PawesomePetsTheme
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import pt.ua.deti.icm.pawesomepets.ui.theme.Typography

@Composable
fun SignUpScreen(
    uiState: SignUpUiState,
    onSignUpClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 50.dp)
    ) {
        AnimatedVisibility(visible = uiState.error != null) {
            uiState.error?.let {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Red)
                ) {
                    Text(
                        text = it,
                        Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
        Column(
            Modifier
                .fillMaxWidth(0.8f)
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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
            OutlinedTextField(
                value = uiState.email,
                onValueChange = uiState.onEmailChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Email")
                }
            )
            OutlinedTextField(
                value = uiState.password,
                onValueChange = uiState.onPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Password")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            OutlinedTextField(
                value = uiState.confirmPassword,
                onValueChange = uiState.onConfirmPasswordChange,
                Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(25),
                label = {
                    Text(text = "Confirm Password")
                },
                visualTransformation = PasswordVisualTransformation()
            )
            Button(
                onClick = onSignUpClick,
                Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}

@Preview(showBackground = true, name = "Default")
@Composable
fun SignUpScreenPreview() {
    PawesomePetsTheme {
        SignUpScreen(
            uiState = SignUpUiState(),
            onSignUpClick = {}
        )
    }
}

@Preview(showBackground = true, name = "With error")
@Composable
fun SignUpScreen1Preview() {
    PawesomePetsTheme {
        SignUpScreen(
            uiState = SignUpUiState(
                error = "Error Sign Up"
            ),
            onSignUpClick = {}
        )
    }
}