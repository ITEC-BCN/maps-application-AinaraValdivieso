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
    //La navegación comienza en la pantalla de permisos
    NavHost(navController, Permisions) {
        composable<Permisions> {
            PermissionsScreen() {
                navController.navigate(LogIn)
            }
        }
        //Dentro de iniciar sesión puede llevar al menú (has iniciado sesión correctamente)
        //o a la pantalla de registro (debes de crear un usuario)
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

        //Se debería poder volver a la pantalla de login después de iniciar sesión
        composable<Drawer> {
            DrawerScreen() {
                navController.navigate(LogIn) {
                    popUpTo(0) { inclusive = true }
                }
            }
        }

    }
}