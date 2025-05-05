package com.example.mapsapp.data

import kotlinx.serialization.Serializable


@Serializable
data class Marker(
    val id: Int? = null,
    val title : String,
    val description : String,
    val image : String,
    val lat : Double,
    val lng: Double
)