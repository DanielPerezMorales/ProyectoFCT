package com.example.proyectofct.data.ktor.model

import com.example.proyectofct.data.retrofit.model.FacturaItem
import kotlinx.serialization.Serializable


@Serializable
data class factura_model(
    val numFacturas: String,
    val facturas: List<factura_item_model>
)

data class factura_item_model(
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
)

fun factura_item_model.toFacturaItem(): FacturaItem {
    return FacturaItem(descEstado, importeOrdenacion, fecha)
}