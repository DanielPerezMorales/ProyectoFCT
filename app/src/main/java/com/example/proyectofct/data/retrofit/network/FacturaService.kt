package com.example.proyectofct.data.retrofit.network

import android.util.Log
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.ModeloFactura
import retrofit2.Retrofit
import javax.inject.Inject

class FacturaService @Inject constructor(
    private val retrofit: Retrofit
){

    suspend fun getFacturas(): List<FacturaItem>? {
        val response = retrofit.create(ApiService::class.java).getAllFacturas()
        if (response.isSuccessful) {
            val myResponse: ModeloFactura? = response.body()
            if (myResponse != null) { return myResponse.facturas }
        }
        return null
    }
}