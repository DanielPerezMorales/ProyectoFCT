package com.example.proyectofct.data.mock

import android.util.Log
import co.infinum.retromock.Retromock
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import com.example.proyectofct.data.retrofit.model.ModeloFactura
import com.example.proyectofct.data.retrofit.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class Mock @Inject constructor(private val retromock: Retromock){
    fun getFacturasMOCK(): ModeloFactura? {
        val response = retromock.create(ApiService::class.java).getFacturasMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<ModeloFactura?> =response.execute()
            return myresponseMock.body()
        }
        return null
    }

    fun getDetallesMOCK(): ModeloDetalles? {
        val response = retromock.create(ApiService::class.java).getDetallesMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<ModeloDetalles?> =response.execute()
            return myresponseMock.body()
        }
        return null
    }
}