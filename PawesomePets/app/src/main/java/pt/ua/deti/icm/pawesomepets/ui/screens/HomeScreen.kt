package pt.ua.deti.icm.pawesomepets.ui.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.rememberImagePainter
import pt.ua.deti.icm.pawesomepets.ui.viewmodels.PetsListViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import pt.ua.deti.icm.pawesomepets.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    //onNavigate: (Int) -> Unit,
    onNewPetClick: () -> Unit,
    onPetClick: () -> Unit,
    onQRCodeClick: () -> Unit,
    onGenerateQRCodeClick: () -> Unit,
) {

    val homeViewModel = viewModel(modelClass = PetsListViewModel::class.java)
    val homeState = homeViewModel.state

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        item {
            Button(
                onClick = onQRCodeClick,
                modifier = Modifier
                    .padding(4.dp)
                    .height(56.dp)
                    .width(180.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "QR code scanner",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Scan QR Code", fontSize = 16.sp)
            }
        }
        item {
            Button(
                onClick = onGenerateQRCodeClick,
                modifier = Modifier
                    .padding(4.dp)
                    .height(56.dp)
                    .width(180.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode,
                    contentDescription = "QR code generator",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text("Generate QR Code", fontSize = 16.sp)
            }
        }

        // My pets text
        item {
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "My Pets",
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        }

        // Lista de pets do utilizador
        item {
            PetCardsRow(onAddPetClick = onNewPetClick, onPetClick = onPetClick)
        }

        // Adicionar PET


        // Petsitters near by text
        item {
            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Petsitters Near By",
                    fontFamily = FontFamily.Serif,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .padding(start = 16.dp)
                )
            }
        }

        // Lista de petsitters
        item {
            PetsittersColumn()
        }
    }
}

// Lista de pets do utilizador
@Composable
fun PetCardsRow(
    onAddPetClick: () -> Unit,
    onPetClick: () -> Unit,
) {
    val pets = listOf(
        Pair("Pet 1", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkK9T6L5UXh400z3raAhMfCiwrYJMRRzoQ-L2_jE6_nIi9mIeiC92azLdHCCY6t867EVY&usqp=CAU"),
        Pair("Pet 2", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkK9T6L5UXh400z3raAhMfCiwrYJMRRzoQ-L2_jE6_nIi9mIeiC92azLdHCCY6t867EVY&usqp=CAU"),
        Pair("Pet 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkK9T6L5UXh400z3raAhMfCiwrYJMRRzoQ-L2_jE6_nIi9mIeiC92azLdHCCY6t867EVY&usqp=CAU"),
        Pair("Pet 4", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkK9T6L5UXh400z3raAhMfCiwrYJMRRzoQ-L2_jE6_nIi9mIeiC92azLdHCCY6t867EVY&usqp=CAU"),
        Pair("Pet 5", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQkK9T6L5UXh400z3raAhMfCiwrYJMRRzoQ-L2_jE6_nIi9mIeiC92azLdHCCY6t867EVY&usqp=CAU")
    )

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(pets) { pet ->
            PetCard(petName = pet.first, petImageUrl = pet.second, onPetClick = onPetClick)
        }
        item {
            PetCard(petName = "Add Pet", petImageUrl = "https://upload.wikimedia.org/wikipedia/commons/thumb/9/9e/Plus_symbol.svg/1200px-Plus_symbol.svg.png", onPetClick = onAddPetClick)
        }
    }
}

// Card de cada pet
@Composable
fun PetCard(petName: String, petImageUrl: String, onPetClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(120.dp)
            .height(170.dp)
            .padding(8.dp)
            .clickable { onPetClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = petImageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = petName,
                fontFamily = FontFamily.Serif,
                fontSize = 16.sp
            )
        }
    }
}


// Lista de petsitters
@Composable
fun PetsittersColumn() {
    val petsitters = listOf(
        Triple("Petsitter 1", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", "2 km away"),
        Triple("Petsitter 2", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", "5 km away"),
        Triple("Petsitter 3", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", "8 km away"),
        Triple("Petsitter 4", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", "10 km away"),
        Triple("Petsitter 5", "https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png", "12 km away")
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        petsitters.forEach { petsitter ->
            PetsitterCard(
                name = petsitter.first,
                imageUrl = petsitter.second,
                distance = petsitter.third
            )
        }
    }
}

// Card de cada petsitter
@Composable
fun PetsitterCard(name: String, imageUrl: String, distance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = imageUrl,
                    builder = {
                        crossfade(true)
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = name,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = distance,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}
