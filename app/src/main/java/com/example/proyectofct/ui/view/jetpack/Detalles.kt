package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.ui.viewmodel.DetallesViewModel


private val viewModel: DetallesViewModel = DetallesViewModel()
private val detallesObject = Detalles_Object
private val alert = Alert()

@Composable
fun Detalles(context: Context) {
    Column(
        Modifier
            .fillMaxHeight()
            .background(color = colorResource(id = R.color.white))
    ) {
        Detalles_Body(context = context)
    }
}

@Composable
private fun Detalles_Body(context: Context) {
    Column(Modifier.padding(10.dp)) {
        var isLoading by remember { mutableStateOf(true) }

        var textCAU by remember { mutableStateOf("") }
        var textTipo by remember { mutableStateOf("") }
        var textEstado by remember { mutableStateOf("") }
        var textExcedentes by remember { mutableStateOf("") }
        var textPotencia by remember { mutableStateOf("") }

        viewModel.cargarDetalles(context, detallesObject)

        viewModel.detallesLiveData.observe(context as LifecycleOwner) { detalles ->
            textCAU = detalles.CAU
            textTipo = detalles.Tipo
            textEstado = detalles.solicitud
            textExcedentes = detalles.Excedentes
            textPotencia = detalles.Potencia

            isLoading = false
        }

        if (isLoading) {
            EditText(stringResource(id = R.string.cau), textCAU, isLoading)
            EditTextWithButton(
                stringResource(id = R.string.estado_solicitud),
                textEstado,
                isLoading,
                context
            )
            EditText(stringResource(id = R.string.tipo_autoconsumo), textTipo, isLoading)
            EditText(
                stringResource(id = R.string.comprobacion_de_excedentes),
                textExcedentes,
                isLoading
            )
            EditText(stringResource(id = R.string.potencia_de_instalacion), textPotencia, isLoading)
        } else {
            EditText(stringResource(id = R.string.cau), textCAU, isLoading)
            EditTextWithButton(
                stringResource(id = R.string.estado_solicitud),
                textEstado,
                isLoading,
                context
            )
            EditText(stringResource(id = R.string.tipo_autoconsumo), textTipo, isLoading)
            EditText(
                stringResource(id = R.string.comprobacion_de_excedentes),
                textExcedentes,
                isLoading
            )
            EditText(stringResource(id = R.string.potencia_de_instalacion), textPotencia, isLoading)
        }
    }
}


@Composable
private fun EditText(text: String, textoMutable: String, enabled: Boolean) {
    var textEmail by remember { mutableStateOf(textoMutable) }
    Column(Modifier.padding(10.dp)) {
        Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextField(
            value = textEmail,
            onValueChange = { newText ->
                textEmail = newText
            }, textStyle = TextStyle(fontSize = 17.sp),
            enabled = enabled,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = colorResource(id = R.color.white))
        )
    }
}

@Composable
private fun EditTextWithButton(
    text: String,
    textoMutable: String,
    enabled: Boolean,
    context: Context
) {
    Column(Modifier.padding(10.dp)) {
        Text(text = text, fontSize = 20.sp, modifier = Modifier.padding(bottom = 10.dp))
        TextFieldWithIcon(textoMutable, enabled, context)
    }
}

@Composable
private fun TextFieldWithIcon(textoMutable: String, enabled: Boolean, context: Context) {
    var textEmail by remember { mutableStateOf(textoMutable) }
    TextField(
        value = textEmail,
        onValueChange = { newText ->
            ;
            textEmail = newText
        },
        textStyle = TextStyle(fontSize = 17.sp),
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(id = R.color.white)),
        trailingIcon = {
            IconButton(onClick = { alert.showPopNative_Context(context) }) {
                Image(
                    painter = painterResource(id = R.drawable.informacion),
                    contentDescription = "ojo informacion"
                )
            }
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun Detalles_Preview() {
    //Detalles()
}