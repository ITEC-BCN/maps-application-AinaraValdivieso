package com.example.mapsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.mapsapp.data.Marker
import com.example.mapsapp.viewmodels.SupabaseViewModel


@Composable
fun MarkerListScreen(navigateToDetail: (String) -> Unit) {
    val supabaseViewModel = viewModel<SupabaseViewModel>()
    val markerList by supabaseViewModel.markersList.observeAsState(emptyList<Marker>())
    supabaseViewModel.getAllMarkers()

    Column(
        Modifier
            .fillMaxSize()
            .padding(top = 120.dp, start = 16.dp, end = 16.dp)
    ) {
        //Mostrar todos los marcadores uno a uno en una LazyColumn
        LazyColumn(
            Modifier.fillMaxWidth()
        ) {
            items(markerList) { marker ->
                val dissmissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = {
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            supabaseViewModel.deleteMarker(marker.id.toString(), marker.image)
                            true
                        } else false
                    }
                )
                //Para que al deslizar se elimine el marcador
                SwipeToDismissBox(
                    state = dissmissState,
                    backgroundContent = {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.Red),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 24.dp)
                            )
                        }
                    }
                ) {
                    MarkerItem(marker) { navigateToDetail(marker.id.toString()) }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

//Diseño e información de un marcador
@Composable
fun MarkerItem(marker: Marker, navigateToDetail: (String) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF2C2C2C))
            .clickable { navigateToDetail(marker.id.toString()) }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = rememberAsyncImagePainter(marker.image),
                contentDescription = "Imagen del marcador",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = marker.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = marker.description,
                    fontSize = 14.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}

