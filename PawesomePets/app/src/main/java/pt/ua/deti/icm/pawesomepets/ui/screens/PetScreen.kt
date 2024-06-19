package pt.ua.deti.icm.pawesomepets.ui.screens

import android . graphics . Bitmap
import android . graphics . ImageDecoder
import android . net . Uri
import android . os . Build
import android . provider . MediaStore
import android . util . Log
import android . widget . Toast
import androidx . activity . ComponentActivity
import androidx . activity . compose . rememberLauncherForActivityResult
import androidx . activity . result . contract . ActivityResultContracts
import androidx . compose . foundation . background
import androidx . compose . foundation . clickable
import androidx . compose . foundation . layout . Box
import androidx . compose . foundation . layout . Column
import androidx . compose . foundation . layout . fillMaxSize
import androidx . compose . foundation . layout . padding
import androidx . compose . foundation . layout . size
import androidx . compose . foundation . shape . CircleShape
import androidx . compose . foundation . Image
import androidx . compose . foundation . layout . Arrangement
import androidx . compose . foundation . layout . Row
import androidx . compose . foundation . layout . Spacer
import androidx . compose . foundation . layout . fillMaxWidth
import androidx . compose . foundation . layout . height
import androidx . compose . foundation . layout . width
import androidx . compose . foundation . shape . RoundedCornerShape
import androidx . compose . foundation . text . KeyboardOptions
import androidx . compose . material3 . Button
import androidx . compose . material3 . CircularProgressIndicator
import androidx . compose . material3 . ExperimentalMaterial3Api
import androidx . compose . material3 . Icon
import androidx . compose . material3 . MaterialTheme
import androidx . compose . material3 . Text
import androidx . compose . material3 . TextField
import androidx . compose . runtime . Composable
import androidx . compose . runtime . LaunchedEffect
import androidx . compose . runtime . getValue
import androidx . compose . runtime . mutableStateOf
import androidx . compose . runtime . remember
import androidx . compose . runtime . setValue
import androidx . compose . ui . Alignment
import androidx . compose . ui . Modifier
import androidx . compose . ui . draw . clip
import androidx . compose . ui . graphics . Color
import androidx . compose . ui . graphics . asImageBitmap
import androidx . compose . ui . layout . ContentScale
import androidx . compose . ui . platform . LocalContext
import androidx . compose . ui . res . painterResource
import androidx . compose . ui . text . TextStyle
import androidx . compose . ui . text . font . FontWeight
import androidx . compose . ui . text . input . ImeAction
import androidx . compose . ui . unit . dp
import androidx . compose . ui . unit . sp
import coil . compose . rememberImagePainter
import com . google . firebase . Firebase
import com . google . firebase . database . database
import com . google . firebase . storage . FirebaseStorage
import com . google . firebase . storage . ListResult
import com . google . firebase . storage . StorageReference
import com . google . firebase . storage . storage
import pt . ua . deti . icm . pawesomepets . R
import java . io . ByteArrayOutputStream

data class Edit(val breed: String, val imageUrl: String)

fun getLastImageFromFirebase(callback: (String?) -> Unit) {
    val storageRef: StorageReference = FirebaseStorage.getInstance().reference.child("images")
    storageRef.listAll()
        .addOnSuccessListener { listResult: ListResult ->
            if (listResult.items.isNotEmpty()) {
                val mostRecentItem = listResult.items.maxByOrNull { it.name }
                mostRecentItem?.downloadUrl?.addOnSuccessListener { uri ->
                    callback(uri.toString())
                }?.addOnFailureListener {
                    callback(null)
                }
            } else {
                callback(null)
            }
        }
        .addOnFailureListener { exception ->
            Log.e("FirebaseStorage", "Error fetching images: ${exception.message}")
            callback(null)
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetScreen() {

    var imgUrl by remember { mutableStateOf("") }
    val database = Firebase.database
    val isUploading = remember { mutableStateOf(false) }

    val empty by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("Dory") }
    var breed by remember { mutableStateOf("Dog") }

    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    val launcherImage = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                ImageDecoder.decodeBitmap(source)
            }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmapResult ->
        bitmapResult?.let {
            bitmap = it
        }
    }

    LaunchedEffect(Unit) {
        getLastImageFromFirebase { imageUrl ->
            if (imageUrl != null) {
                imgUrl = imageUrl
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (bitmap != null) {
            Image(
                bitmap = bitmap?.asImageBitmap()!!,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        } else {
            Image(
                painter = rememberImagePainter(imgUrl),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(200.dp)
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    }

    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.padding(top = 160.dp, start = 240.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_photo_camera_24),
            contentDescription = null,
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.inversePrimary)
                .size(50.dp)
                .padding(10.dp)
                .clickable { showDialog = true }
        )
    }

    Column(
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        if (showDialog) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .width(300.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
            ) {
                Column(modifier = Modifier.padding(start = 60.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                launcher.launch(null)
                                showDialog = false
                            }
                    )
                    Text(
                        text = "Camera",
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
                Spacer(modifier = Modifier.padding(30.dp))
                Column {
                    Image(
                        painter = painterResource(id = R.drawable.baseline_image_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clickable {
                                launcherImage.launch("image/*")
                                showDialog = false
                            }
                    )
                    Text(
                        text = "Gallery",
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 80.dp)
                ) {
                    Text(
                        text = "✖",
                        color = MaterialTheme.colorScheme.inverseOnSurface,
                        modifier = Modifier
                            .clickable {
                                showDialog = false
                            }
                    )
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 270.dp)
    ) {
        TextField(
            value = name,
            onValueChange = {
                name = it
            },
            label = {
                Text(text = "Name")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pets_24),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            enabled = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            value = breed,
            onValueChange = {
                breed = it
            },
            label = {
                Text(text = "Breed")
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_pets_24),
                    contentDescription = null
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            singleLine = true,
            textStyle = TextStyle(
                fontSize = 16.sp
            ),
            enabled = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                isUploading.value = true
                bitmap.let { bitmap ->
                    if (bitmap != null) {
                        uploadImageToFirebase(
                            bitmap,
                            context as ComponentActivity
                        ) { success, imageUrl ->
                            isUploading.value = false
                            if (success) {
                                imageUrl.let {
                                    imgUrl = it
                                }
                                val editRef = database.reference.child("Name")
                                val petRef = editRef.child(name)
                                val pet = Edit(breed, imgUrl)
                                petRef.setValue(pet)
                                Toast.makeText(
                                    context,
                                    "Pet edited successfully",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(context, "Pet not edited", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }
            }
        ) {
            Text(
                text = "Edit",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(330.dp)
    ) {
        if (isUploading.value) {
            CircularProgressIndicator(
                modifier = Modifier.padding(16.dp),
                color = Color.White
            )
        }
    }
}

fun uploadImageToFirebase(bitmap: Bitmap, context: ComponentActivity, callback: (Boolean, String) -> Unit ) {
    val storageRef = Firebase.storage.reference
    val imageRef = storageRef.child("images/${bitmap}")

    val baos = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val imageData = baos.toByteArray()

    imageRef.putBytes(imageData).addOnSuccessListener {
        imageRef.downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            callback(true, imageUrl)
        }.addOnFailureListener {
            callback(false, null.toString())
        }
    }.addOnFailureListener {
        callback(false, null.toString())
    }
}