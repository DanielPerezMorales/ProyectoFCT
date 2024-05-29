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

@Composable
fun AppNavigation(context: Context) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AppScreens.Login.route) {
        composable(route = AppScreens.Login.route) {
            LoginIberdrola(navController, context)
        }
        composable(
            route = AppScreens.Menuprincipal.route + "/{email}" + "/{pass}" + "/{check}" + "/{date}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("pass") { type = NavType.StringType },
                navArgument("check") { type = NavType.BoolType },
                navArgument("date") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email")
            val pass = backStackEntry.arguments?.getString("pass")
            val check = backStackEntry.arguments?.getBoolean("check")
            val date = backStackEntry.arguments?.getString("date")
            Menu_principal(navController, email, pass, check, date)
        }
        composable(route = AppScreens.Registro.route) {
            RegistroIberdrola(navController = navController, context)
        }
        composable(route = AppScreens.FG.route) {
            FGIberdrola(navController = navController, context = context)
        }
        composable(route = AppScreens.Navegacion.route) {
            Navegacion(context = context, navController)
        }
        composable(route = AppScreens.SmartSolar.route) {
            SS_Pantalla(navController = navController, context)
        }
        composable(
            route = AppScreens.Facturas.route + "/{mock}" + "/{remoteConfig}" + "/{KTOR}",
            arguments = listOf(navArgument(name = "mock") {
                type = NavType.BoolType
            },
                navArgument(name = "remoteConfig") {
                    type = NavType.BoolType
                },
                navArgument(name = "KTOR") {
                    type = NavType.BoolType
                }
            )
        ) {
            FacturasIberdrola(
                navController = navController,
                context = context,
                it.arguments!!.getBoolean("mock"),
                it.arguments!!.getBoolean("remoteConfig"),
                it.arguments!!.getBoolean("KTOR")
            )
        }

    }
}