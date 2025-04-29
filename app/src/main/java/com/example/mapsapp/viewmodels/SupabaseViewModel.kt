package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SupabaseViewModel : ViewModel() {
    val database = MyApp.database

    private val _markerTitle = MutableLiveData<String>()
    val markerTitle = _markerTitle

    private val _markerDescription = MutableLiveData<String>()
    val markerDescription = _markerDescription


    private val _markerImage = MutableLiveData<String>()
    val markerImage = _markerImage

    private val _markersList = MutableLiveData<List<Marker>>()
    val markersList = _markersList

    fun insertNewMarker(title : String, desc : String, image : String) {
        val newMarker = Marker(title = title, description = desc, image = image)
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarker(newMarker)
            database.getAllMarkers()
        }
    }


    fun getAllMarkers() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseStudents = database.getAllMarkers()
            withContext(Dispatchers.Main) {
                _markersList.value = databaseStudents
            }
        }
    }



    fun editMarkerTitle(title: String) {
        _markerTitle.value = title
    }

    fun editMarkerDesc(desc: String) {
        _markerDescription.value = desc
    }

    fun editMarkerImage(image : String){
        _markerImage.value = image
    }

}