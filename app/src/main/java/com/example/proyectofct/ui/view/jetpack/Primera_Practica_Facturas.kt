package com.example.proyectofct.ui.view.jetpack

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.di.RoomModule
import com.example.proyectofct.ui.viewmodel.FacturasViewModel

private val facturaModule = RoomModule
private val facturaViewModel = FacturasViewModel()
private val alert = Alert()

@Composable
fun FacturasIberdrola(navController: NavController?, context: Context?) {
    BodyFacturas(navController, context)
}

@Composable
fun BodyFacturas(navController: NavController?, context: Context?) {
    Facturas(navController, context = context)
}

@Composable
fun Facturas(navController: NavController?, context: Context?) {
    var facturas by remember { mutableStateOf<List<facturaItem>>(emptyList()) }
    val isLoading = remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
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
                    IconButton(onClick = { /* Handle filter */ }) {
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
        facturaViewModel.fetchFacturas(facturaModule.provideRoom(context!!))
    }

    facturaViewModel.facturas.observe(context as LifecycleOwner, Observer {
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
                .padding(bottom = 10.dp)
        ) {
            Column(Modifier.padding(10.dp)) {
                Text(
                    text = factura.fecha,
                    fontSize = 20.sp,
                    color = Color.Black
                )
                Text(
                    text = factura.descEstado,
                    fontSize = 18.sp,
                    color = if (
                        factura.descEstado != "Pagada"
                    ) {
                        colorResource(id = R.color.color_estado_factura)
                    } else {
                        colorResource(id = R.color.black)
                    },
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
            Text(
                text = "${factura.importeOrdenacion}€",
                fontSize = 23.sp,
                color = Color.Black,
                modifier = if(factura.descEstado == "Pendiente de pago") {
                    Modifier.padding(
                        top = 20.dp,
                        start = 50.dp,
                        bottom = 20.dp,
                        end = 20.dp
                    )
                } else {
                    Modifier.padding(
                        top = 20.dp,
                        start = 90.dp,
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


@Preview(showSystemUi = true)
@Composable
fun PreviewFacturas() {
    FacturasIberdrola(navController = null, context = null)

}