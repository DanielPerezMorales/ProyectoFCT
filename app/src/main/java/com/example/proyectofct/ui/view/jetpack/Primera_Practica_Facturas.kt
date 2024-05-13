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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
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
    Facturas(context = context)
}

@Composable
fun Facturas(context: Context?) {
    var facturas = remember { mutableListOf<facturaItem>() }
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
                    IconButton(onClick = { /* Handle navigation */ }) {
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
        ) {
            if (context != null) {
                facturaViewModel.fetchFacturas(facturaModule.provideRoom(context = context))
                facturaViewModel.facturas.observe(context as LifecycleOwner, Observer {
                    if (it != null) {
                        facturas = it as MutableList<facturaItem>
                    }
                })
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
}

@Composable
fun FacturaItem(factura: facturaItem, onItemClick: () -> Unit) {
    Row (Modifier.fillMaxWidth()){
        Column(
            modifier = Modifier
                .clickable(onClick = onItemClick).padding(10.dp)
        ) {
            Text(
                text = factura.fecha,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = factura.descEstado,
                fontSize = 18.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Text(
            text = factura.importeOrdenacion.toString(),
            fontSize = 23.sp,
            color = Color.Black
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun PreviewFacturas() {
    FacturasIberdrola(navController = null, context = null)
}