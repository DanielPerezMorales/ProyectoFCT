package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.proyectofct.core.AppNavigation
import com.example.proyectofct.ui.view.activity.ui.theme.ProyectoFCTTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            ProyectoFCTTheme {
                androidx.compose.material.Surface(color = androidx.compose.material.MaterialTheme.colors.background) {
                    AppNavigationWithBackHandler(this)
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

@Composable
fun AppNavigationWithBackHandler(activity: ComponentActivity) {
    AppNavigation(activity)
    BackHandler {
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        activity.startActivity(intent)
        activity.finish()
    }
}
