package com.example.proyectofct.ui.view.jetpack

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.di.RoomModule
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar

private val facturaModule = RoomModule
private val alert = Alert()

@Composable
fun FacturasIberdrola(
    navController: NavController?,
    context: Context?,
    viewmodel: FacturasViewModel?,
    boolean: Boolean
) {
    BodyFacturas(navController, context, viewmodel, boolean)
}

@Composable
fun BodyFacturas(navController: NavController?, context: Context?, viewmodel: FacturasViewModel?, boolean: Boolean) {
    Facturas(navController, context = context, viewmodel, boolean)
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Facturas(navController: NavController?, context: Context?, viewmodel: FacturasViewModel?, boolean: Boolean) {
    var facturas by remember { mutableStateOf<List<facturaItem>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }
    var isFiltredOpen = remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf(Triple(0, 0, 0)) }
    val stringnormla = stringResource(id = R.string.dia_mes_anio)
    var isDatePickerDialogVisible by remember { mutableStateOf(false) }
    var isBotonDesde by remember { mutableStateOf(false) }
    var DesdeFecha by remember { mutableStateOf(stringnormla) }
    var HastaFecha by remember { mutableStateOf(stringnormla) }
    var precio by remember { mutableStateOf(0.0f) }
    var isCheckedPagada by remember { mutableStateOf(false) }
    var isCheckedPendiente by remember { mutableStateOf(false) }
    var isCheckedPlanDePago by remember { mutableStateOf(false) }
    var isCheckedAnuladas by remember { mutableStateOf(false) }
    var isCheckedCuotaFija by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        if (isFiltredOpen.value) {
            Box(modifier = Modifier.fillMaxSize()) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = "") },
                            backgroundColor = Color.Transparent,
                            elevation = 0.dp,
                            actions = {
                                IconButton(onClick = { isFiltredOpen.value = false }) {
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
                            androidx.compose.material.Button(
                                onClick = {
                                    isDatePickerDialogVisible = true
                                    isBotonDesde = true
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.gris_boton
                                    )
                                ),
                                contentPadding = PaddingValues(13.dp)
                            ) {
                                Text(text = DesdeFecha, color = Color.Black)
                            }
                            androidx.compose.material.Button(
                                onClick = { isDatePickerDialogVisible = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.gris_boton
                                    )
                                ),
                                contentPadding = PaddingValues(13.dp)
                            ) {
                                Text(text = HastaFecha, color = Color.Black)
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
                            colors = SliderColors(
                                activeTickColor = colorResource(id = R.color.transparente),
                                disabledActiveTickColor = colorResource(id = R.color.transparente),
                                activeTrackColor = colorResource(id = R.color.color_consumo),
                                disabledActiveTrackColor = colorResource(id = R.color.transparente),
                                disabledInactiveTickColor = colorResource(id = R.color.color_consumo),
                                disabledInactiveTrackColor = colorResource(id = R.color.color_consumo),
                                disabledThumbColor = colorResource(id = R.color.color_consumo),
                                inactiveTickColor = colorResource(id = R.color.color_consumo),
                                inactiveTrackColor = colorResource(id = R.color.gris_boton),
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
                                androidx.compose.material3.Text(text = "Anulada")
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
                        Spacer(modifier = Modifier.height(12.dp))

                        fun checkBox(): MutableList<String> {
                            val entrees: MutableList<String> = mutableListOf()
                            if (isCheckedPagada) {
                                entrees.add("Pagada")
                            }
                            if (isCheckedAnuladas) {
                                entrees.add("Anulada")
                            }
                            if (isCheckedCuotaFija) {
                                entrees.add("Cuota Fija")
                            }
                            if (isCheckedPendiente) {
                                entrees.add("Pendiente de pago")
                            }
                            if (isCheckedPlanDePago) {
                                entrees.add("Plan de pago")
                            }
                            return entrees
                        }

                        fun listadoFiltrado(): MutableList<String> {
                            val entrees: MutableList<String> = mutableListOf()
                            if (checkBox().isNotEmpty()) {
                                entrees.add("CheckBox")
                            }
                            if (DesdeFecha != stringnormla && HastaFecha != stringnormla) {
                                entrees.add("Fechas")
                            }
                            return entrees
                        }

                        @SuppressLint("SimpleDateFormat")
                        suspend fun apply(value: Float) {
                            val lista: List<FacturaEntity> =
                                facturaModule.provideRoom(context!!).getFactureDao()
                                    .getAllFacturas()
                            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
                            val fechaInicioText = DesdeFecha
                            val fechaFinText = HastaFecha
                            val listaCheck: MutableList<String> = checkBox()
                            val fechaInicio =
                                if (fechaInicioText != "dia/mes/año") formatoFecha.parse(
                                    fechaInicioText
                                ) else null
                            val fechaFin =
                                if (fechaFinText != "dia/mes/año") formatoFecha.parse(fechaFinText) else null
                            viewmodel?.filtrado(
                                precio = value,
                                fechaInicio = fechaInicio,
                                fechaFin = fechaFin,
                                listaCheck = listaCheck,
                                lista,
                                listadoFiltrado()
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            androidx.compose.material.Button(
                                onClick = {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        apply(precio)
                                        delay(1000)
                                    }
                                    isFiltredOpen.value = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.color_consumo
                                    )
                                ),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.btn_filtrar),
                                    color = Color.White
                                )
                            }
                            androidx.compose.material.Button(
                                onClick = {
                                    DesdeFecha = stringnormla
                                    HastaFecha = stringnormla
                                    precio = 0F
                                    isCheckedAnuladas = false
                                    isCheckedPagada = false
                                    isCheckedPendiente = false
                                    isCheckedCuotaFija = false
                                    isCheckedPlanDePago = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = colorResource(
                                        id = R.color.gris_boton
                                    )
                                ),
                                contentPadding = PaddingValues(16.dp)
                            ) {
                                Text(
                                    text = stringResource(id = R.string.eliminar),
                                    color = Color.Black
                                )
                            }
                        }

                        if (isDatePickerDialogVisible) {
                            if (isBotonDesde) {
                                DatePickerDialog { year, month, day ->
                                    selectedDate = Triple(year, month, day)
                                    DesdeFecha = mostrarResultado(
                                        selectedDate.first,
                                        selectedDate.second,
                                        selectedDate.third
                                    )
                                    isDatePickerDialogVisible = false
                                    isBotonDesde = false
                                }
                            } else {
                                DatePickerDialog { year, month, day ->
                                    selectedDate = Triple(year, month, day)
                                    HastaFecha = mostrarResultado(
                                        selectedDate.first,
                                        selectedDate.second,
                                        selectedDate.third
                                    )
                                    isDatePickerDialogVisible = false
                                }
                            }
                        }
                    }
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                title = {
                    Text(
                        text = stringResource(id = R.string.consumo),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = R.color.color_consumo),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController?.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_arrow_back_ios_new_24),
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isFiltredOpen.value = true }) {
                        Icon(
                            painter = painterResource(id = R.drawable.filtericon_3x),
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
        Text(
            text = stringResource(id = R.string.facturas),
            fontSize = 40.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(start = 20.dp, bottom = 30.dp)
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(start = 20.dp, bottom = 20.dp)
                .fillMaxSize()
        ) {
            if (isLoading.value) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(50.dp)
                        .align(Alignment.Center)
                )
            }
            LazyColumn {
                items(facturas) { factura ->
                    FacturaItem(factura = factura) {
                        if (context != null) {
                            alert.showAlertInformation(
                                "Información",
                                "Esta funcionalidad aún no está disponible",
                                context
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        if(boolean){
            viewmodel?.putRetroMock(context!!, facturaModule.provideRoom(context))
        } else {
            viewmodel?.fetchFacturas(facturaModule.provideRoom(context!!))
        }
    }

    viewmodel?.facturas?.observe(context as LifecycleOwner, Observer {
        if (it != null) {
            if (it.isNotEmpty()) {
                facturas = it
                isLoading.value = false
            }
        }
    })
}


@Composable
fun FacturaItem(factura: facturaItem, onItemClick: () -> Unit) {
    Column {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable(onClick = onItemClick)
        ) {
            Column(
                modifier = if (factura.descEstado == "Pagada") {
                    Modifier.align(alignment = Alignment.CenterVertically)
                } else {
                    Modifier
                }
            ) {
                Text(
                    text = factura.fecha,
                    fontSize = 20.sp,
                    color = Color.Black,
                    modifier = Modifier
                        .padding(
                            top = 10.dp,
                            end = 10.dp,
                            start = 10.dp,
                            bottom = if (factura.descEstado == "Pagada") 10.dp else 0.dp
                        )
                )
                if (factura.descEstado != "Pagada") {
                    Text(
                        text = factura.descEstado,
                        fontSize = 18.sp,
                        color = colorResource(id = R.color.color_estado_factura),
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
            Text(
                text = "${factura.importeOrdenacion}€",
                fontSize = 23.sp,
                color = Color.Black,
                modifier = if (factura.descEstado == "Pendiente de pago") {
                    Modifier.padding(
                        top = 20.dp,
                        start = if (factura.importeOrdenacion.toString().length == 4) 60.dp else 50.dp,
                        bottom = 20.dp,
                        end = 20.dp
                    )
                } else {
                    Modifier.padding(
                        top = 20.dp,
                        start = if (factura.importeOrdenacion.toString().length == 4) 120.dp else 90.dp,
                        bottom = 20.dp,
                        end = 20.dp
                    )
                }
            )
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                    contentDescription = null,
                    modifier = Modifier.padding(20.dp)
                )
            }
        }
        Divider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp
        )
    }
}

fun createDatePickerDialog(
    context: Context,
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
): DatePickerDialog {
    val c = Calendar.getInstance()
    val year = c.get(Calendar.YEAR)
    val month = c.get(Calendar.MONTH)
    val day = c.get(Calendar.DAY_OF_MONTH)

    return DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDay ->
            onDateSelected(selectedYear, selectedMonth, selectedDay)
        },
        year,
        month,
        day
    )
}

@Composable
fun DatePickerDialog(
    onDateSelected: (year: Int, month: Int, day: Int) -> Unit
) {
    val context = LocalContext.current
    val dialog = remember { createDatePickerDialog(context, onDateSelected) }

    // Show the dialog when this composable is first composed
    dialog.show()
}

fun mostrarResultado(year: Int, month: Int, day: Int): String {
    return if (month + 1 < 10) {
        if (day < 10) {
            "0$day/0${month + 1}/$year"
        } else {
            "$day/0${month + 1}/$year"
        }
    } else {
        if (day < 10) {
            "0$day/${month + 1}/$year"
        } else {
            "$day/${month + 1}/$year"
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewFacturas() {
    Facturas(navController = null, context = null, viewmodel = null, false)
}