package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.lifecycle.LifecycleOwner
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.ui.viewmodel.DetallesViewModel


private val alert= Alert()
private val viewModel: DetallesViewModel = DetallesViewModel()
private val detallesObject = Detalles_Object
@Composable
fun Detalles(context: Context) {
    Detalles_Body(context)
}

@Composable
private fun Detalles_Body(context: Context) {
    Column(Modifier.padding(10.dp)) {
        var textCAU by remember { mutableStateOf("") }
        var textTipo by remember { mutableStateOf("") }
        var textEstado by remember { mutableStateOf("") }
        var textExcedentes by remember { mutableStateOf("") }
        var textPotencia by remember { mutableStateOf("") }

        viewModel.detallesLiveData.observe(context as LifecycleOwner, { detalles ->
            textCAU = detalles.CAU
            textTipo = detalles.Tipo
            textEstado = detalles.solicitud
            textExcedentes = detalles.Excedentes
            textPotencia = detalles.Potencia
        })

        viewModel.cargarDetalles(context,detallesObject)

        Log.i("TAG JETPACK",textCAU)
        Log.i("TAG JETPACK",textEstado)
        Log.i("TAG JETPACK",textExcedentes)
        Log.i("TAG JETPACK",textTipo)
        Log.i("TAG JETPACK",textPotencia)

        if (textCAU == ""){

        } else {
            EditText(stringResource(id = com.example.proyectofct.R.string.cau),textCAU)
            EditText(stringResource(id = com.example.proyectofct.R.string.estado_solicitud), textEstado)
            EditText(stringResource(id = com.example.proyectofct.R.string.tipo_autoconsumo), textTipo)
            EditText(stringResource(id = com.example.proyectofct.R.string.comprobacion_de_excedentes), textExcedentes)
            EditText(stringResource(id = com.example.proyectofct.R.string.potencia_de_instalacion), textPotencia)
        }


    }
}

@Composable
private fun EditText(text: String, textoMutable:String) {
    var textEmail by remember { mutableStateOf(textoMutable) }
    Column {
        Text(text = text, fontSize = 20.sp)
        EditText(textEmail, text) { newText ->
            textEmail = newText
        }
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showSystemUi = true)
@Composable
fun Detalles_Preview() {
    //Detalles()
}