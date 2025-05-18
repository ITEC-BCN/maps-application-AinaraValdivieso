package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.mapsapp.ui.navigation.Destination.*
import com.example.mapsapp.ui.screens.*

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun InternalNavigationWrapper(navController: NavHostController, padding: Modifier) {
    //La navegación dentro del menú comienza en la pantalla del mapa
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
            //Recoger las coordenadas para la creación del marcador
            val coords = backStackEntry.toRoute<MarkerCreation>()
            CreateMarkerScreen(coords.latitud, coords.longitud) {}
        }

        composable<Lista> {
            //coger el id para hacer la lista
            MarkerListScreen() { id ->
                navController.navigate(MarkerDetail(id))
            }
        }
        composable<MarkerDetail> { backStackEntry ->
            //Coger el id del marcador seleccionado para así ver únicamente su información
            val detalle = backStackEntry.toRoute<MarkerDetail>()
            DetailMarkerScreen(detalle.id) {
                navController.navigate(Lista) {
                    popUpTo<Lista> { inclusive = true }
                }
            }
        }

    }

}