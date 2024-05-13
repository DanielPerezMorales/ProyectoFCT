package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Composable
fun Filtrado(navController: NavController?, context:Context?){
    BodyFiltrado(navController, context)
}

@Composable
fun BodyFiltrado(navController: NavController?, context:Context?){
    Column {
        DatePicker()
    }
}

@Composable
fun DatePicker(){
    
}

@Preview(showSystemUi = true)
@Composable
fun PreviewFiltrado(){
    Filtrado(navController = null, context = null)
}