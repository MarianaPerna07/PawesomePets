package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.ua.deti.icm.pawesomepets.ui.states.DogFormUiState
import pt.ua.deti.icm.pawesomepets.ui.theme.PawesomePetsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogFormScreen(
    uiState: DogFormUiState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Column(modifier) {
        val topAppBarTitle = uiState.topAppBarTitle
        val deleteEnabled = uiState.isDeleteEnabled
        TopAppBar(
            title = {
                Text(
                    text = topAppBarTitle,
                    fontSize = 20.sp,
                )
            },
            actions = {
                if (deleteEnabled) {
                    Icon(
                        Icons.Filled.Delete,
                        contentDescription = "Delete dog icon",
                        Modifier
                            .clip(CircleShape)
                            .clickable {
                                onDeleteClick()
                            }
                            .padding(4.dp)
                    )
                }
                Icon(
                    Icons.Filled.Done,
                    contentDescription = "Save dog icon",
                    Modifier
                        .clip(CircleShape)
                        .clickable {
                            onSaveClick()
                        }
                        .padding(4.dp)
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF03A9F4))
        )
        Spacer(modifier = Modifier.size(8.dp))
        val name = uiState.name
        val description = uiState.description
        val titleFontStyle = TextStyle.Default.copy(fontSize = 24.sp)
        val descriptionFontStyle = TextStyle.Default.copy(fontSize = 18.sp)
        BasicTextField(
            value = name,
            onValueChange = uiState.onNameChange,
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                if (name.isEmpty()) {
                    Text(
                        text = "Name",
                        style = titleFontStyle.copy(
                            color = Color.Gray.copy(alpha = 0.5f)
                        ),
                    )
                }
                innerTextField()
            },
            textStyle = titleFontStyle
        )
        Spacer(modifier = Modifier.size(16.dp))
        BasicTextField(
            value = description, onValueChange = uiState.onDescriptionChange,
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            decorationBox = { innerTextField ->
                if (description.isEmpty()) {
                    Text(
                        text = "Description",
                        style = descriptionFontStyle
                            .copy(
                                color = Color.Gray.copy(alpha = 0.5f)
                            )
                    )
                }
                innerTextField()
            },
            textStyle = descriptionFontStyle
        )
    }
}


@Preview(showBackground = true)
@Composable
fun DogFormScreenPreview() {
    PawesomePetsTheme {
        DogFormScreen(
            uiState = DogFormUiState(
                topAppBarTitle = "Create Dog"
            ),
            onSaveClick = {},
            onDeleteClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DogFormScreenWithEditModePreview() {
    PawesomePetsTheme {
        DogFormScreen(
            uiState = DogFormUiState(
                topAppBarTitle = "Edit Dog",
                isDeleteEnabled = true
            ),
            onSaveClick = {},
            onDeleteClick = {},
        )
    }
}