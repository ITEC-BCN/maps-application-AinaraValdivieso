package com.example.mapsapp.data

import com.example.mapsapp.BuildConfig
import com.example.mapsapp.utils.AuthState
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.user.UserSession
import io.github.jan.supabase.createSupabaseClient

class SupabaseManager {
    private val supabaseUrl = BuildConfig.SUPABASE_URL
    private val supabaseKey = BuildConfig.SUPABASE_KEY
    private val supabase = createSupabaseClient(
        supabaseUrl = supabaseUrl,
        supabaseKey = supabaseKey
    ) {
        install(Auth) {
            autoLoadFromStorage = true
        }
    }

    //Registrar usuario a partir del email
    suspend fun signUpWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            supabase.auth.signUpWith(Email) {
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }

    //Iniciar sesión
    suspend fun signInWithEmail(emailValue: String, passwordValue: String): AuthState {
        try {
            supabase.auth.signInWith(Email) {
                email = emailValue
                password = passwordValue
            }
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }

    //Recuperar la sessión cuando ya la has iniciado y no has cerrado sesión
    fun retrieveCurrentSession(): UserSession?{
        val session = supabase.auth.currentSessionOrNull()
        return session
    }
    fun refreshSession(): AuthState {
        try {
            supabase.auth.currentSessionOrNull()
            return AuthState.Authenticated
        } catch (e: Exception) {
            return AuthState.Error(e.localizedMessage)
        }
    }



}
