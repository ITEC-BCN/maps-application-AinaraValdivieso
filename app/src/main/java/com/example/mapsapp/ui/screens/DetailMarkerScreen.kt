package com.example.mapsapp.ui.screens

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.viewmodels.SupabaseViewModel

@Composable
fun DetailMarkerScreen(id: String, navigateBack: () -> Unit) {
    val supabaseViewModel = viewModel<SupabaseViewModel>()
    val markerTitle by supabaseViewModel.markerTitle.observeAsState("")
    val markerDesc by supabaseViewModel.markerDesc.observeAsState("")
    val marker by supabaseViewModel.marker.observeAsState()
    val showLoading : Boolean by supabaseViewModel.showLoading.observeAsState(true)




    //Parte cámara
    val context = LocalContext.current
    val imageUri = remember { mutableStateOf<Uri?>(null) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val takePictureLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success && imageUri.value != null) {
                val stream = context.contentResolver.openInputStream(imageUri.value!!)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }

    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                imageUri.value = it
                val stream = context.contentResolver.openInputStream(it)
                bitmap.value = BitmapFactory.decodeStream(stream)
            }
        }
    if (showDialog) {
        AlertDialog(onDismissRequest = { showDialog = false }, title = { Text("Selecciona una opción") },
            text = { Text("¿Quieres tomar una foto o elegir una desde la galería?") },
            confirmButton = {TextButton(onClick = {
                showDialog = false
                val uri = createImageUri(context)
                imageUri.value = uri
                takePictureLauncher.launch(uri!!)
            }) { Text("Tomar Foto") }
            },
            dismissButton = {TextButton(onClick = {
                showDialog = false
                pickImageLauncher.launch("image/*")
            }) { Text("Elegir de Galería") }
            }
        )
    }
    if (showLoading){
        supabaseViewModel.getMarker(id)
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.secondary
            )
        }
    } else{
        Column(Modifier.fillMaxSize()) {
            Column(
                Modifier.fillMaxWidth().weight(0.4f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Update marker ${marker!!.title}", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                TextField(
                    value = markerTitle,
                    onValueChange = { supabaseViewModel.editMarkerTitle(it) })
                TextField(
                    value = markerDesc,
                    onValueChange = { supabaseViewModel.editMarkerDesc(it) })
                //Aquí se pone la cámara
                Button(
                    onClick = { showDialog = true  },
                ) {
                    Text("Abrir Cámara o Galería")
                }

                Spacer(modifier = Modifier.height(24.dp))

                bitmap.value?.let {
                    Image(
                        bitmap = it.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(300.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Button(onClick = {
                    supabaseViewModel.updateMarker(
                        id,
                        markerTitle,
                        markerDesc,
                        bitmap.value,
                        marker!!.lat,
                        marker!!.lng

                    )

                }) {
                    Text("Update")
                }
                Button(
                    onClick = navigateBack,
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text("Tornar",
                        modifier = Modifier.padding(5.dp))
                }
            }

        }

    }
}

/*
    val viewModel : SupabaseViewModel = viewModel()
    val marker by viewModel.marker.observeAsState()
    val showLoading : Boolean by viewModel.showLoading.observeAsState(true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        if (showLoading){
            viewModel.getMarker(id)
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .background(Color(0xFFC4DAFF))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(marker!!.image),
                        contentDescription = "Imagen del marcador seleccionado",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .size(150.dp)
                    )
                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(
                            text = marker!!.title.uppercase(),
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.Black
                        )
                        Text(
                            text = marker!!.description,
                            modifier = Modifier.padding(bottom = 8.dp),
                            color = Color.Black

                        )

                        Button(
                            onClick = {},//ir a update
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text("Edit marker")
                        }
                        Button(
                            onClick = navigateBack,
                            modifier = Modifier.padding(top = 2.dp)
                        ) {
                            Text("Tornar",
                                modifier = Modifier.padding(5.dp))
                        }
                    }

                }

            }
        }
    }
 */