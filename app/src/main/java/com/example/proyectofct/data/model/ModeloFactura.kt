package com.example.proyectofct.data.model


import android.annotation.SuppressLint
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat

data class ModeloFactura(
    @SerializedName("numFacturas") val numFacturas: String,
    @SerializedName("facturas") val facturas: List<FacturaItem>
)

data class FacturaItem(
    @SerializedName("descEstado") val descEstado: String,
    @SerializedName("importeOrdenacion") val importeOrdenacion: Float,
    @SerializedName("fecha") val fecha: String
)

@SuppressLint("SimpleDateFormat")
fun FacturaItem.toFacturaEntity(): FacturaEntity {
    val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
    val dateFecha = formatoFecha.parse(fecha)
    return FacturaEntity(fecha = dateFecha!!, estado = descEstado, precio = importeOrdenacion)
}

