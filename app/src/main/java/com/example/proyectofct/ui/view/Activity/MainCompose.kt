package com.example.proyectofct.ui.view.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofct.core.AppNavigation
import com.example.proyectofct.ui.view.activity.ui.theme.ProyectoFCTTheme
import com.example.proyectofct.ui.viewmodel.DetallesViewModel
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCompose : ComponentActivity() {
    private val facturasViewModel: FacturasViewModel by viewModels()
    private val loginviewModel:LoginViewModel by viewModels()
    private val foViewModel: ForgotPasswordViewModel by viewModels()
    private val SignUpviewModel:SignUpViewModel by viewModels()
    private val DetallesviewModel:DetallesViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ProyectoFCTTheme {
                androidx.compose.material.Surface(color = androidx.compose.material.MaterialTheme.colors.background) {
                    AppNavigation(this, loginviewModel, SignUpviewModel, foViewModel, facturasViewModel, DetallesviewModel)
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