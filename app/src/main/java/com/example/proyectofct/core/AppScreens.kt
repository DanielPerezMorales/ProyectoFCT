package com.example.proyectofct.core

sealed class AppScreens (val route:String){
    object Login : AppScreens("login")
    object Registro : AppScreens("registro")
    object FG : AppScreens("FG")
    object menu_principal : AppScreens("menu_principal")
    object facturas : AppScreens("practica1")
}