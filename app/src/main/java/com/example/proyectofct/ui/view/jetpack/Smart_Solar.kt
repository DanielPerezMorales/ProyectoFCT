package com.example.proyectofct.ui.view.jetpack

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.material.tabs.TabLayout

@Composable
fun SS_Pantalla(navController: NavController?) {
    SS_Body(navController)
}

@Composable
fun SS_Body(navController: NavController?) {
    Column (Modifier.background(color = colorResource(id = com.example.proyectofct.R.color.white))){
        ParteDeArriba(navController)
        Spacer(modifier = Modifier.height(10.dp))
        TextoGrande()
        Spacer(modifier = Modifier.height(10.dp))
        TabScreen()
    }
}

@Composable
private fun ParteDeArriba(navController: NavController?) {
    TopAppBar(
        backgroundColor = colorResource(id = com.example.proyectofct.R.color.transparente),
        elevation = 0.dp
    ) {
        IconButton(onClick = { navController?.popBackStack() }) {
            Icon(
                painter = painterResource(id = com.example.proyectofct.R.drawable.baseline_arrow_back_ios_new_24),
                contentDescription = null
            )
        }
        Text(
            text = stringResource(id = com.example.proyectofct.R.string.atras),
            color = colorResource(
                id = com.example.proyectofct.R.color.color_consumo
            )
        )
    }
}

@Composable
private fun TextoGrande() {
    Text(
        text = stringResource(id = com.example.proyectofct.R.string.smart_solar),
        fontSize = 30.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 20.dp)
    )
}

@Composable
private fun TabScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Mi instalación", "Energía", "Detalles")

    Column(modifier = Modifier.fillMaxWidth()) {
        TabRow(
            selectedTabIndex = tabIndex,
            backgroundColor = colorResource(id = com.example.proyectofct.R.color.transparente)
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(text = { Text(title) },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index }
                )
            }
        }
        when (tabIndex) {
            0 -> Instalacion()
            1 -> Energia()
            2 -> Detalles()
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Preview_SS() {
    SS_Pantalla(null)
}