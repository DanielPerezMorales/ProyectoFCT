package com.example.proyectofct.data.model

import com.google.gson.annotations.SerializedName

data class Modelo_Detalles (
    @SerializedName("Código Autoconsumo") val CAU: String,
    @SerializedName("Estado solicitud alta autoconsumidor") val solicitud: String,
    @SerializedName("Tipo autoconsumo") val Tipo: String,
    @SerializedName("Comprobación de excedentes") val Excedentes: String,
    @SerializedName("Potencia de instalación") val Potencia: String
)