package com.example.proyectofct.data

import android.content.Context
import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.data.model.modelo_Factura
import com.example.proyectofct.data.network.ApiService
import retrofit2.Response


class Mock (private val context: Context){

    var retromock = RetrofitHelper.getRetromock(context)

    fun getFacturasMOCK(): modelo_Factura? {
        val response = retromock.create(ApiService::class.java).getFacturasMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<modelo_Factura?> =response.execute()
            if (myresponseMock != null) {
                return myresponseMock.body()
            }
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }

    fun getDetallesMOCK(): Modelo_Detalles? {
        val response = retromock.create(ApiService::class.java).getDetallesMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<Modelo_Detalles?> =response.execute()
            if (myresponseMock != null) {
                return myresponseMock.body()
            }
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }
}