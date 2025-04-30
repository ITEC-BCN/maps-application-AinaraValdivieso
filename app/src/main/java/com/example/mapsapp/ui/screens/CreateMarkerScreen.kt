package com.example.mapsapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.viewmodels.SupabaseViewModel

@Composable
fun CreateMarkerScreen(){
    val supabaseViewModel = viewModel<SupabaseViewModel>()
    val markerTitle: String by supabaseViewModel.markerTitle.observeAsState("")
    val markerDescription: String by supabaseViewModel.markerDescription.observeAsState("")
    val markerImage : String by supabaseViewModel.markerImage.observeAsState("")
    Column(Modifier.fillMaxSize()) {
        Column(
            Modifier.fillMaxWidth().weight(0.4f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Create new student", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            TextField(
                value = markerTitle,
                onValueChange = { supabaseViewModel.editMarkerTitle(it) })
            TextField(
                value = markerDescription,
                onValueChange = { supabaseViewModel.editMarkerDesc(it) })
            //Aquí se pone la cámara
            TextField(
                value = markerImage,
                onValueChange = { supabaseViewModel.editMarkerImage(it) })
            Button(onClick = {
                supabaseViewModel.insertNewMarker(
                    markerTitle,
                    markerDescription,
                    markerImage
                )
            }) {
                Text("Insert")
            }
        }

    }
}

