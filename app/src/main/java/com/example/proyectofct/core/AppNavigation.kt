package com.example.proyectofct.core

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.proyectofct.ui.view.jetpack.FGIberdrola
import com.example.proyectofct.ui.view.jetpack.FacturasIberdrola
import com.example.proyectofct.ui.view.jetpack.LoginIberdrola
import com.example.proyectofct.ui.view.jetpack.Menu_principal
import com.example.proyectofct.ui.view.jetpack.Navegacion
import com.example.proyectofct.ui.view.jetpack.RegistroIberdrola
import com.example.proyectofct.ui.view.jetpack.SS_Pantalla
import com.example.proyectofct.ui.viewmodel.FacturasViewModel

@Composable
fun AppNavigation(context: Context, viewmodel:FacturasViewModel) {
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
        composable(route = AppScreens.Navegacion.route) {
            Navegacion(context = context)
        }
        composable(route = AppScreens.SmartSolar.route) {
            SS_Pantalla(navController = navController, context)
        }
        composable(route= AppScreens.facturas.route + "{mock}", arguments = listOf(navArgument(name="mock"){
            type = NavType.BoolType
        })){
            FacturasIberdrola(navController = navController, context = context, viewmodel, it.arguments!!.getBoolean("mock"))
        }

    }
}