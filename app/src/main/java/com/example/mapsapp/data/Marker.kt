package com.example.mapsapp.data

import kotlinx.serialization.Serializable


@Serializable
data class Marker(
    val id: Int? = null,
    var title : String,
    var description : String,
    var image : String,
    val lat : Double,
    val lng: Double,
    val user_id : String? = null
)