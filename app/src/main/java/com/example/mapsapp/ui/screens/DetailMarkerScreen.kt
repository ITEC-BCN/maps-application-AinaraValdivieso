package com.example.mapsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.viewmodels.SupabaseViewModel

@Composable
fun DetailMarkerScreen(id: String, navigateBack: () -> Unit) {
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
                    .background(Color(0xFF9F93A8))
                    .clip(shape = RoundedCornerShape(16.dp))
                    .padding(top = 10.dp, bottom = 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(marker!!.image),
                        contentDescription = "Imagen de la película seleccionada",
                        modifier = Modifier
                            .padding(bottom = 16.dp)
                            .size(200.dp)
                    )
                    Column {
                        Text(
                            text = marker!!.title.uppercase(),
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = marker!!.description,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                    }

                }
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