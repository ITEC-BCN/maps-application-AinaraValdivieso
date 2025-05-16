package com.example.mapsapp.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.graphics.vector.ImageVector

enum class DraweItem(
    val icon: ImageVector,
    val text: String,
    val route: Destination
)
{
    MAP(Icons.Default.LocationOn, "Map", Destination.Map),
    LIST(Icons.Default.Edit, "List", Destination.Lista)

}