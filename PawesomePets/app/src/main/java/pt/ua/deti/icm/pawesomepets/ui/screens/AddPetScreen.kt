package pt.ua.deti.icm.pawesomepets.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AddPetViewModel
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.AddPetViewModelFactory
import pt.ua.deti.icm.pawesomepets.ui.repository.Repository
import pt.ua.deti.icm.pawesomepets.data.room.models.Breed

@Composable
fun AddPetScreen(
    repository: Repository,
    onPetAdded: () -> Unit
) {
    val viewModel: AddPetViewModel = viewModel(factory = AddPetViewModelFactory(repository))
    val petName by viewModel.petName.collectAsState()
    val petAge by viewModel.petAge.collectAsState()
    val petBreed by viewModel.petBreed.collectAsState()
    val breeds by viewModel.breeds.collectAsState()
    val isAdopted by viewModel.isAdopted.collectAsState()
    val description by viewModel.description.collectAsState()

    var showAddBreedDialog by remember { mutableStateOf(false) }
    var newBreedName by remember { mutableStateOf("") }
    var newSpecies by remember { mutableStateOf("") }

    if (showAddBreedDialog) {
        AlertDialog(
            onDismissRequest = { showAddBreedDialog = false },
            title = { Text("Add New Breed") },
            text = {
                Column {
                    OutlinedTextField(
                        value = newBreedName,
                        onValueChange = { newBreedName = it },
                        label = { Text("Breed Name") }
                    )
                    OutlinedTextField(
                        value = newSpecies,
                        onValueChange = { newSpecies = it },
                        label = { Text("Species") }
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    viewModel.addBreed(newBreedName, newSpecies)
                    newBreedName = ""
                    newSpecies = ""
                    showAddBreedDialog = false
                }) {
                    Text("Add Breed")
                }
            },
            dismissButton = {
                Button(onClick = { showAddBreedDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        OutlinedTextField(
            value = petName,
            onValueChange = { viewModel.onPetNameChange(it) },
            label = { Text("Pet Name") }
        )

        OutlinedTextField(
            value = petAge,
            onValueChange = { viewModel.onPetAgeChange(it) },
            label = { Text("Pet Age") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )

        BreedDropdownMenu(
            label = "Breed",
            items = breeds,
            selectedItem = petBreed,
            onItemSelected = { viewModel.onPetBreedChange(it) }
        )

        OutlinedTextField(
            value = description,
            onValueChange = { viewModel.onDescriptionChange(it) },
            label = { Text("Description") }
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = isAdopted,
                onCheckedChange = { viewModel.onIsAdoptedChange(it) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Adopted")
        }

        Button(onClick = { showAddBreedDialog = true }) {
            Text("Add New Breed")
        }

        Button(onClick = {
            viewModel.addPet()
            onPetAdded()
        }) {
            Text("Add Pet")
        }
    }
}

@Composable
fun BreedDropdownMenu(
    label: String,
    items: List<Breed>,
    selectedItem: Breed?,
    onItemSelected: (Breed) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(selectedItem?.breedName ?: "") }

    Box(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = { },
            label = { Text(label) },
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            trailingIcon = {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null
                    )
                }
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                CustomDropdownMenuItem(onClick = {
                    selectedText = item.breedName
                    expanded = false
                    onItemSelected(item)
                }) {
                    Text(text = item.breedName)
                }
            }
        }
    }
}



@Composable
fun CustomDropdownMenuItem(
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = onClick
            )
            .padding(8.dp)
    ) {
        content()
    }
}



