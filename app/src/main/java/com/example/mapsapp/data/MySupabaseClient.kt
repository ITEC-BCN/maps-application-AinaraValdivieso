package com.example.mapsapp.data


import android.os.Build
import androidx.annotation.RequiresApi
import com.example.mapsapp.BuildConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class MySupabaseClient {
    lateinit var client: SupabaseClient
    lateinit var storage: Storage
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY
    constructor() {
        client = createSupabaseClient(supabaseUrl = supabaseUrl, supabaseKey = supabaseKey) {
            install(Postgrest)
            install(Storage)
            install(Auth){autoLoadFromStorage = true}
        }
        storage = client.storage
    }

    //SQL operations
    //Seleccionar tot
    suspend fun getAllMarkers(): List<Marker> {
        return client.from("Markers").select().decodeList<Marker>()
    }

    //Seleccionar solop uno por id
    suspend fun getMarker(id: String): Marker {
        return client.from("Markers").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marker>()
    }

    //Insertar marcador nuevo
    suspend fun insertMarker(marker: Marker){
        client.from("Markers").insert(marker)
    }

    //Actualizar marcador
    suspend fun updateMarker(id: String, title: String, description: String, image: String, imageFile : ByteArray){
        val imageName = storage.from("images").update(path = image, data = imageFile)
        client.from("Markers").update({
            set("title", title)
            set("description", description)
            set("image", buildImageUrl(imageFileName = imageName.path))
        }) { filter { eq("id", id) } }
    }

    //Eliminar marcador
    suspend fun deleteMarker(id: String){
        client.from("Markers").delete{ filter { eq("id", id) } }
    }


    //Subir imagen
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun uploadImage(imageFile: ByteArray): String {
        val fechaHoraActual = LocalDateTime.now()
        val formato = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val imageName = storage.from("images").upload(path = "image_${fechaHoraActual.format(formato)}.png", data = imageFile)
        return buildImageUrl(imageFileName = imageName.path)
    }

    //Borrar imagen
    suspend fun deleteImage(imageName: String){
        val imgName = imageName.removePrefix("https://aobflzinjcljzqpxpcxs.supabase.co/storage/v1/object/public/images/")
        client.storage.from("images").delete(imgName)
    }

    //Construir la ruta donde se guardará la imagen
    fun buildImageUrl(imageFileName: String) = "${this.supabaseUrl}/storage/v1/object/public/images/${imageFileName}"


}
