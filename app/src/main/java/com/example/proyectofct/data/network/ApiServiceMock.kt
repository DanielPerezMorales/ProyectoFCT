package com.example.proyectofct.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.data.model.modelo_Factura
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiServiceMock {
    @GET("datos")
    fun obtenerDatos(): Call<Modelo_Detalles>

    fun obtenerDetalles(): Modelo_Detalles
}