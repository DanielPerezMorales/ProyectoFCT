package com.example.proyectofct.ui.view.jetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Instalacion() {
    Column (
        Modifier
            .fillMaxHeight()
            .background(color = colorResource(id = com.example.proyectofct.R.color.white))){
        Instalacion_Body()
    }
}

@Composable
private fun Instalacion_Body() {
    Column (Modifier.background(color = colorResource(id = com.example.proyectofct.R.color.white))){
        Text(
            text = stringResource(id = com.example.proyectofct.R.string.FGInst_TV_mi_instalación),
            Modifier.padding(15.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row (Modifier.padding(15.dp)){
            Text(
                text = stringResource(id = com.example.proyectofct.R.string.FGInst_autoconsumo) + " "
            )
            Text(
                text = stringResource(
                    id = com.example.proyectofct.R.string.FGInst_porcentaje_autoconsumo
                ), fontWeight = FontWeight.Bold
            )
        }
        ImagenInstalacion()
    }
}

@Composable
private fun ImagenInstalacion() {
    Image(
        painter = painterResource(id = com.example.proyectofct.R.drawable.grafico_mi_instalacion),
        contentDescription = null,
        Modifier
            .size(400.dp)
            .fillMaxWidth()
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewInstalacion() {
    Instalacion()
}