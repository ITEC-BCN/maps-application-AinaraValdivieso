package com.example.mapsapp

import android.app.Application
import com.example.mapsapp.data.MySupabaseClient

class MyApp: Application() {
    companion object {
        lateinit var database: MySupabaseClient
    }
    override fun onCreate() {
        super.onCreate()
        database = MySupabaseClient(
            supabaseUrl = "https://ngdbfxljdebzesmtjyfc.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5nZGJmeGxqZGViemVzbXRqeWZjIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NDU5MjA1MjMsImV4cCI6MjA2MTQ5NjUyM30.Q-oZRU3j2FFkyA-y5SZOYJTg2cu7ToWQaaiMyvN3WD4"
        )
    }
}
