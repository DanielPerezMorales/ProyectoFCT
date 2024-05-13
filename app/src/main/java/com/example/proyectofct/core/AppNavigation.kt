package com.example.proyectofct.core

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofct.ui.view.jetpack.FGIberdrola
import com.example.proyectofct.ui.view.jetpack.Facturas
import com.example.proyectofct.ui.view.jetpack.FacturasIberdrola
import com.example.proyectofct.ui.view.jetpack.Filtrado
import com.example.proyectofct.ui.view.jetpack.LoginIberdrola
import com.example.proyectofct.ui.view.jetpack.Menu_principal
import com.example.proyectofct.ui.view.jetpack.RegistroIberdrola

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Login.route) {
        composable(route = AppScreens.Login.route) {
            LoginIberdrola(navController, context)
        }
        composable(route = AppScreens.menu_principal.route) {
            Menu_principal(navController)
        }
        composable(route = AppScreens.Registro.route) {
            RegistroIberdrola(navController = navController, context)
        }
        composable(route = AppScreens.FG.route) {
            FGIberdrola(navController = navController, context = context)
        }
        composable(route = AppScreens.facturas.route) {
            FacturasIberdrola(navController = navController, context = context)
        }
        composable(route = AppScreens.filtrado.route) {
            Filtrado(navController = navController, context = context)
        }
    }
}