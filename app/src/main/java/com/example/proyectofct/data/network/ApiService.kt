package com.example.proyectofct.data.network

import co.infinum.retromock.meta.Mock
import co.infinum.retromock.meta.MockResponse
import com.example.proyectofct.data.model.modelo_Factura
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("facturas")
    suspend fun getAllFacturas(): Response<modelo_Factura>

    @Mock
    @MockResponse(body = "facturas.json")
    @GET("facturas")
    fun getFacturasMock(): Call<modelo_Factura?>
}