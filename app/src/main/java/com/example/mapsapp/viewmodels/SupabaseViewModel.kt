package com.example.mapsapp.viewmodels

import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mapsapp.SupabaseApplication
import com.example.mapsapp.data.Marker
import io.ktor.util.Digest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream

class SupabaseViewModel : ViewModel() {
    val database = SupabaseApplication.database

    private val _selectedMarker = MutableLiveData<Marker>()
    val selectedMarker = _selectedMarker

    private val _markerTitle = MutableLiveData<String>()
    val markerTitle = _markerTitle

    private val _markerDesc = MutableLiveData<String>()
    val markerDesc = _markerDesc

    private val _markersList = MutableLiveData<List<Marker>>()
    val markersList = _markersList

    private val _marker = MutableLiveData<Marker>()
    val marker = _marker

    private val _showLoading = MutableLiveData<Boolean>(true)
    val showLoading = _showLoading

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertNewMarker(title: String, desc: String, image: Bitmap?, lat: Double, lng: Double) {
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        CoroutineScope(Dispatchers.IO).launch {
            val imageName = database.uploadImage(stream.toByteArray())
            val newMarker = Marker(title = title, description = desc, image = imageName, lat = lat, lng = lng)
            database.insertMarker(newMarker)
        }
    }


    fun updateMarker(id: String, title : String, desc : String, image : Bitmap?){
        val stream = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.PNG, 0, stream)
        val imageName = _selectedMarker.value?.image?.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        CoroutineScope(Dispatchers.IO).launch {
            database.updateMarker(id, title, desc, imageName.toString(), stream.toByteArray())
        }
    }




    fun getAllMarkers() {
        CoroutineScope(Dispatchers.IO).launch {
            val databaseMarkers = database.getAllMarkers()
            withContext(Dispatchers.Main) {
                _markersList.value = databaseMarkers
                _showLoading.value = false
            }
        }
    }

    fun getMarker(id : String){
        CoroutineScope(Dispatchers.IO).launch {
            val marker = database.getMarker(id)
            withContext(Dispatchers.Main) {
                _marker.value = marker
                _showLoading.value = false
            }
        }
    }

    fun deleteStudent(id: String, image : String){
        CoroutineScope(Dispatchers.IO).launch {
            database.deleteImage(image)
            database.deleteMarker(id)
            getAllMarkers()
        }
    }




    fun editMarkerTitle(title: String) {
        _markerTitle.value = title
    }

    fun editMarkerDesc(desc: String) {
        _markerDesc.value = desc
    }


}