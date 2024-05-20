package com.example.proyectofct.data.retrofit.network

import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.ModeloFactura

class FacturaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getFacturas(): List<FacturaItem>? {
        val response = retrofit.create(ApiService::class.java).getAllFacturas()
        if (response.isSuccessful) {
            val myResponse: ModeloFactura? = response.body()
            if (myResponse != null) { return myResponse.facturas }
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }
}