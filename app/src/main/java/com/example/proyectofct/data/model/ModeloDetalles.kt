package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class ModeloDetalles (
    @SerializedName("Código Autoconsumo") val cau: String,
    @SerializedName("Estado solicitud alta autoconsumidor") val solicitud: String,
    @SerializedName("Tipo autoconsumo") val tipo: String,
    @SerializedName("Comprobación de excedentes") val excedentes: String,
    @SerializedName("Potencia de instalación") val potencia: String
)