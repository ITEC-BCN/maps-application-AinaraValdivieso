package com.example.mapsapp.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.data.Marker
import com.example.mapsapp.viewmodels.SupabaseViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapScreen(modifier: Modifier = Modifier) {
    val supabaseViewModel = viewModel<SupabaseViewModel>()
    val markerList by supabaseViewModel.markersList.observeAsState(emptyList<Marker>())

    Column(modifier.fillMaxSize()) {
        //He cambiado la Longitud un poco para que quede más cercano al ITB
        val itb = LatLng(41.4534225, 2.1862701)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState){
            Marker(
                state = MarkerState(position = itb),
                title = "ITB",
                snippet = "Marker at ITB"
            )

            markerList.forEach {
                //Poner que por cada marker se vea en el mapa con el símbolo
                //Cómo pongo que se creen (LongClick pero como)
                val coords = LatLng(supabaseViewModel.markerLat.value!!, supabaseViewModel.markerLng.value!!)
                Marker(
                    //Me falta añadir las coordenadas al supabase
                    state = MarkerState(position = coords),
                    title = supabaseViewModel.markerTitle.value,
                )
            }
        }
    }
}
