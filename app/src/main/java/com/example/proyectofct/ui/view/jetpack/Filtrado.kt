package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectofct.R

@Composable
fun Filtrado(navController:NavController?, context: Context?) {
    var precio by remember { mutableStateOf(0.0f) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "") },
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                actions = {
                    IconButton(onClick = { navController?.popBackStack()}) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_32),
                            contentDescription = "Close"
                        )
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = stringResource(id = R.string.filtrar_facturas),
                fontSize = 35.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Text(
                text = stringResource(id = R.string.fecha_emision),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 14.dp)
            )
            Row {
                Text(
                    text = stringResource(id = R.string.filtrado_facturas_desde),
                    color = colorResource(id = R.color.gris),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 10.dp)
                )
                Text(
                    text = stringResource(id = R.string.filtrado_facturas_hasta),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.gris),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 128.dp)
                )
            }
            Row {
                Button(
                    onClick = {
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.gris_boton)),
                    contentPadding = PaddingValues(13.dp)
                ) {
                    Text(text = stringResource(id = R.string.dia_mes_anio), color = Color.Black)
                }
                Button(
                    onClick = { /* Handle clear filter button click */ },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 5.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.gris_boton)),
                    contentPadding = PaddingValues(13.dp)
                ) {
                    Text(text = stringResource(id = R.string.dia_mes_anio), color = Color.Black)
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.por_un_importe),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            Row {
                Text(text = "0")
                Text(text = precio.toInt().toString(), Modifier.padding(start = 165.dp))
                Text(text = "100", Modifier.padding(start = 130.dp))
            }
            Slider(
                value = precio,
                onValueChange = { newValue ->
                    precio = newValue
                },
                valueRange = 0.0F..100.0F,
                colors = SliderColors(activeTickColor = colorResource(id = R.color.color_consumo),
                    disabledActiveTickColor = colorResource(id = R.color.color_consumo),
                    activeTrackColor = colorResource(id = R.color.color_consumo),
                    disabledActiveTrackColor = colorResource(id = R.color.color_consumo),
                    disabledInactiveTickColor = colorResource(id = R.color.color_consumo),
                    disabledInactiveTrackColor = colorResource(id = R.color.color_consumo),
                    disabledThumbColor = colorResource(id = R.color.color_consumo),
                    inactiveTickColor = colorResource(id = R.color.color_consumo),
                    inactiveTrackColor = colorResource(id = R.color.color_consumo),
                    thumbColor = colorResource(id = R.color.color_consumo)
                    )
            )
            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.Gray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(id = R.string.por_estado),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 10.dp)
            )
            CheckBoxs()
            Spacer(modifier = Modifier.height(12.dp))
            ButtonRow()
        }
    }
}

@Composable
fun ButtonRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = { /* Handle filter button click */ },
            modifier = Modifier
                .weight(1f)
                .padding(end = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.color_consumo)),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = stringResource(id = R.string.btn_filtrar), color = Color.White)
        }
        Button(
            onClick = { /* Handle clear filter button click */ },
            modifier = Modifier
                .weight(1f)
                .padding(start = 5.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.gris_boton)),
            contentPadding = PaddingValues(16.dp)
        ) {
            Text(text = stringResource(id = R.string.eliminar), color = Color.Black)
        }
    }
}

@Composable
fun CheckBoxs(){
    var isCheckedPagada by remember { mutableStateOf(false) }
    var isCheckedPendiente by remember { mutableStateOf(false) }
    var isCheckedPlanDePago by remember { mutableStateOf(false) }
    var isCheckedAnuladas by remember { mutableStateOf(false) }
    var isCheckedCuotaFija by remember { mutableStateOf(false) }
    Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedPagada, onCheckedChange = {
                isCheckedPagada = it
            })
            androidx.compose.material3.Text(text = "Pagada")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedAnuladas, onCheckedChange = {
                isCheckedAnuladas = it
            })
            androidx.compose.material3.Text(text = "Anuladas")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedCuotaFija, onCheckedChange = {
                isCheckedCuotaFija = it
            })
            androidx.compose.material3.Text(text = "Cuota Fija")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedPendiente, onCheckedChange = {
                isCheckedPendiente = it
            })
            androidx.compose.material3.Text(text = "Pendiente de pago")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCheckedPlanDePago, onCheckedChange = {
                isCheckedPlanDePago = it
            })
            androidx.compose.material3.Text(text = "Plan de pago")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewFiltrdo() {
    Filtrado(null,null)
}