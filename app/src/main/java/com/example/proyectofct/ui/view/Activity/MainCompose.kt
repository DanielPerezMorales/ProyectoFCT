package com.example.proyectofct.ui.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofct.core.AppNavigation
import com.example.proyectofct.ui.view.activity.ui.theme.ProyectoFCTTheme
import com.example.proyectofct.ui.viewmodel.FacturasViewModel

class MainCompose : ComponentActivity() {
    val FacturasViewModel = FacturasViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ProyectoFCTTheme {
                androidx.compose.material.Surface(color = androidx.compose.material.MaterialTheme.colors.background) {
                    AppNavigation(this, FacturasViewModel)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ProyectoFCTTheme {
        //AppNavigation()
    }
}