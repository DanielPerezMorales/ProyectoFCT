package com.example.proyectofct.data.ktor.model

import com.example.proyectofct.data.retrofit.model.FacturaItem
import kotlinx.serialization.Serializable


@Serializable
data class FacturaModel(
    val numFacturas: String,
    val facturas: List<FacturaItemModel>
)

@Serializable
data class FacturaItemModel(
    val descEstado: String,
    val importeOrdenacion: Float,
    val fecha: String
)

fun FacturaItemModel.toFacturaItem(): FacturaItem {
    return FacturaItem(descEstado, importeOrdenacion, fecha)
}