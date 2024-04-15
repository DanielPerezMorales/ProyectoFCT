package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class modelo_Factura (
    @SerializedName("numFacturas") val numFacturas: String,
    @SerializedName("facturas") val facturas: List<facturaItem>
)

data class facturaItem(
    @SerializedName("descEstado") val descEstado: String,
    @SerializedName("importeOrdenacion") val importeOrdenacion: Float,
    @SerializedName("fecha") val fecha: String
)