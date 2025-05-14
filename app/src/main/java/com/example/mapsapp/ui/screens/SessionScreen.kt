package com.example.mapsapp.ui.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mapsapp.utils.AuthState
import com.example.mapsapp.utils.SharedPreferencesHelper
import com.example.mapsapp.viewmodels.AuthViewModel
import com.example.mapsapp.viewmodels.AuthViewModelFactory
import androidx.compose.runtime.getValue


@Composable
fun SessionScreen(){
    val context = LocalContext.current
    val viewModel: AuthViewModel = viewModel(factory = AuthViewModelFactory(SharedPreferencesHelper(context)))
    val authState by viewModel.authState.observeAsState()
    val showError by viewModel.showError.observeAsState(true)

    if(authState == AuthState.Authenticated){
        //navigateToHome()
    }
    else{
        if (showError) {
            val errorMessage = (authState as AuthState.Error).message
            if (errorMessage.contains("invalid_credentials")) {
                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_LONG).show()
            } else if (errorMessage.contains("weak_password")) {
                Toast.makeText(context, "Password should be at least 6 characters", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(context, "An error has ocurred", Toast.LENGTH_LONG).show()
            }
            viewModel.errorMessageShowed()
        }

    }

}