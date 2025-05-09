package com.example.mapsapp.ui.screens

import android.util.Log
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
fun MapScreen(navigateToCreate: (Double, Double) -> Unit) {
    val supabaseViewModel = viewModel<SupabaseViewModel>()
    val markerList by supabaseViewModel.markersList.observeAsState(emptyList<Marker>())

    supabaseViewModel.getAllMarkers()

    Column(Modifier.fillMaxSize()) {
        //He cambiado la Longitud un poco para que quede más cercano al ITB
        val itb = LatLng(41.4534225, 2.1862701)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(itb, 17f)
        }
        GoogleMap(
            Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapClick = {
                //Solo lo he hecho para comprobar que se me guarde la lista bien
                Log.d("MARKER LIST", markerList.toString())
            },
            onMapLongClick = {
                Log.d("MAP CLICKED LONG", it.toString())
                navigateToCreate(it.latitude, it.longitude)
            }
        )
        {
            Marker(
                state = MarkerState(position = itb),
                title = "ITB",
                snippet = "Marker at ITB"
            )


            markerList.forEach { marker ->
                val coords = LatLng(marker.lat, marker.lng)
                Marker(
                    state = MarkerState(position = coords),
                    title = supabaseViewModel.selectedMarker.value?.title,
                )
            }
        }
    }
}
