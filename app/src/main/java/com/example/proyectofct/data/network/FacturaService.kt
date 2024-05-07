package com.example.proyectofct.data.network

import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.modelo_Factura

class FacturaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getFacturas(): List<facturaItem>? {
        val response = retrofit.create(ApiService::class.java).getAllFacturas()
        if (response.isSuccessful) {
            val MyResponse: modelo_Factura? = response.body()
            if (MyResponse != null) { return MyResponse.facturas }
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }
}