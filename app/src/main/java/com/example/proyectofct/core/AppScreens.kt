package com.example.proyectofct.core

sealed class AppScreens (val route:String){
    data object Login : AppScreens("login")
    data object Registro : AppScreens("registro")
    data object FG : AppScreens("FG")
    data object Menuprincipal : AppScreens("menu_principal")
    data object Facturas : AppScreens("practica1")
    data object Navegacion : AppScreens("navegador")
    data object SmartSolar : AppScreens("SS")
}