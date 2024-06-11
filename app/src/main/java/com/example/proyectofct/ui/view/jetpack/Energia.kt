package com.example.proyectofct.ui.view.jetpack

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Energia() {
    Column(
        Modifier
            .fillMaxHeight()
            .background(color = colorResource(id = com.example.proyectofct.R.color.white))
    ) {
        Instalacion_Body()
    }
}

@Composable
private fun Instalacion_Body() {
    Column(
        Modifier
            .background(color = colorResource(id = com.example.proyectofct.R.color.white))
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ImagenInstalacion()
        Spacer(modifier = Modifier.height(30.dp))
        Text(
            text = stringResource(id = com.example.proyectofct.R.string.FGEnergia_TV_Energia),
            Modifier
                .padding(horizontal = 16.dp)
                .width(230.dp)
                .fillMaxHeight()
                .padding(vertical = 16.dp),
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    }
}


@Composable
private fun ImagenInstalacion() {
    Image(
        painter = painterResource(id = com.example.proyectofct.R.drawable.plan_gestiones),
        contentDescription = null,
        Modifier
            .size(300.dp)
    )
}

@Preview(showSystemUi = true)
@Composable
private fun PreviewInstalacion() {
    Energia()
}