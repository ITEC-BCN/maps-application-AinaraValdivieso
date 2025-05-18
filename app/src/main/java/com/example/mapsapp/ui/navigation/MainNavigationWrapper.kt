package com.example.mapsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mapsapp.ui.screens.DrawerScreen
import com.example.mapsapp.ui.screens.PermissionsScreen
import com.example.mapsapp.ui.navigation.Destination.Drawer
import com.example.mapsapp.ui.navigation.Destination.Permisions
import com.example.mapsapp.ui.navigation.Destination.*
import com.example.mapsapp.ui.screens.LogInScreen
import com.example.mapsapp.ui.screens.RegisterScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainNavigationWrapper(){
    val navController = rememberNavController()
    NavHost(navController, Permisions) {
        composable<Permisions> {
            PermissionsScreen() {
                navController.navigate(LogIn)
            }
        }
        composable<LogIn> {
            LogInScreen(
                navigateToHome = { navController.navigate(Drawer) },
                navigateRegister = { navController.navigate(Register) }
            )
        }

        composable<Register> {
            RegisterScreen {
                navController.navigate(Drawer)
            }
        }

        composable<Drawer> {
            DrawerScreen() {
                navController.navigate(LogIn) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }

    }
}