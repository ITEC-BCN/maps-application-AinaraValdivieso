package com.example.mapsapp.data


import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from


class MySupabaseClient() {
    lateinit var client: SupabaseClient
    constructor(supabaseUrl: String, supabaseKey: String): this(){
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Postgrest)
        }
    }
    //SQL operations

    suspend fun getAllMarkers(): List<Marker> {
        return client.from("Markers").select().decodeList<Marker>()
    }

    suspend fun getMarker(id: String): Marker {
        return client.from("Markers").select {
            filter {
                eq("id", id)
            }
        }.decodeSingle<Marker>()
    }

    suspend fun insertMarker(marker: Marker){
        client.from("Markers").insert(marker)
    }
    suspend fun updateMarker(id: String, title: String, description: String, image: String, lat: Double, lng: Double){
        client.from("Markers").update({
            set("title", title)
            set("mark", description)
            set("image", image)
            set("lat", lat)
            set("lng", lng)
        }) { filter { eq("id", id) } }
    }
    suspend fun deleteMarker(id: String){
        client.from("Markers").delete{ filter { eq("id", id) } }
    }


}
