package com.example.mapsapp.ui.navigation

import kotlinx.serialization.Serializable

sealed class Destination {

    @Serializable
    object Permisions : Destination()

    @Serializable
    object Drawer : Destination()

    @Serializable
    object Map : Destination()

    @Serializable
    object List : Destination()

//    @Serializable
//    data class MarkerCreation(val coordenadas: LatLng)

    @Serializable
    data class MarkerDetail(val id: Int)

}