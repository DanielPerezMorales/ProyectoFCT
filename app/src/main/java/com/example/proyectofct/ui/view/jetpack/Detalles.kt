package com.example.proyectofct.ui.view.jetpack

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectofct.core.Alert


private val alert= Alert()
@Composable
fun Detalles() {
    Detalles_Body()
}

@Composable
private fun Detalles_Body() {
    Column(Modifier.padding(10.dp)) {
        EditText(stringResource(id = com.example.proyectofct.R.string.cau))
        EditText(stringResource(id = com.example.proyectofct.R.string.estado_solicitud))
        EditText(stringResource(id = com.example.proyectofct.R.string.tipo_autoconsumo))
        EditText(stringResource(id = com.example.proyectofct.R.string.comprobacion_de_excedentes))
        EditText(stringResource(id = com.example.proyectofct.R.string.potencia_de_instalacion))
    }
}

@Composable
private fun EditText(text: String) {
    var textEmail by remember { mutableStateOf("") }
    Column {
        Text(text = text, fontSize = 20.sp)
        EditText(textEmail, text) { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
private fun EditTextWithButton(text: String) {
    Column {
        Text(text = text, fontSize = 20.sp)
        SpecialEditText(text)
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Composable
private fun SpecialEditText(text: String){
    var textEmail by remember { mutableStateOf("") }
    EditText(textEmail, text) { newText ->
        textEmail = newText
    }
    IconButton(onClick = {
        //alert.showPopNative()
    }) {
        Icon(painter = painterResource(id = com.example.proyectofct.R.drawable.informacion), contentDescription = null, modifier = Modifier.padding(start = 30.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun Detalles_Preview() {
    Detalles()
}