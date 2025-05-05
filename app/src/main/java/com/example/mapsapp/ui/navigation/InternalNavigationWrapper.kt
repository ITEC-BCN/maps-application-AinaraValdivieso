package com.example.mapsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.Destination.*
import com.example.mapsapp.ui.screens.CreateMarkerScreen
import com.example.mapsapp.ui.screens.DetailMarkerScreen
import com.example.mapsapp.ui.screens.MapScreen
import com.example.mapsapp.ui.screens.MarkerListScreen
import kotlin.math.ln

@Composable
fun InternalNavigationWrapper(navController: NavHostController, padding: Modifier) {
    NavHost(navController, Map){
        composable<Destination.Map> {
            MapScreen() { lat, lng ->
                navController.navigate(MarkerCreation(
                    latitud = lat,
                    longitud = lng
                ))
            }
        }
        composable<MarkerCreation> { backStackEntry ->
            val coords = backStackEntry.toRoute<MarkerCreation>()
            CreateMarkerScreen(coords.latitud, coords.longitud) {}
        }

        composable<Lista> {
            MarkerListScreen() { id ->
                navController.navigate(MarkerDetail(id))
            }
        }
        composable<MarkerDetail> { backStackEntry ->
            val detalle = backStackEntry.toRoute<MarkerDetail>()
            DetailMarkerScreen(detalle.id) {
                navController.navigate(Lista) {
                    popUpTo<Lista> { inclusive = true }
                }
            }
        }

    }

}