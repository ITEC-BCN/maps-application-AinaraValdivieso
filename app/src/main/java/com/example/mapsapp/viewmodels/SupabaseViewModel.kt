package com.example.mapsapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.MyApp
import com.example.mapsapp.data.Marker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.ln

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

    fun insertNewMarker(title : String, desc : String, image : String, lat: Double, lng: Double) {
        val newMarker = Marker(title = title, description = desc, image = image, lat = lat, lng = lng)
        CoroutineScope(Dispatchers.IO).launch {
            database.insertMarker(newMarker)
            database.getAllMarkers()
            val markers = database.getAllMarkers()
            _markersList.value = markers
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

    fun deleteStudent(id: String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteMarker(id)
            getAllMarkers()
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