package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient


class MyApp: Application() {
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY
    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        )
    }
}
