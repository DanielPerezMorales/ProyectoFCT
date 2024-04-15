package com.example.proyectofct.data.network

import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.modelo_Factura
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FacturaService {
    private val retrofit = RetrofitHelper.getRetrofit()

    suspend fun getFacturas(): modelo_Factura? {
        val response = retrofit.create(ApiService::class.java).getAllFacturas()
        if (response.isSuccessful) {
            val MyResponse: modelo_Factura? = response.body()
            if (MyResponse != null) {
                return MyResponse
            }
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }
}