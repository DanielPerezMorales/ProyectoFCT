package com.example.proyectofct.data.mock

import android.content.Context
import android.util.Log
import co.infinum.retromock.Retromock
import com.example.proyectofct.data.retrofit.RetrofitHelper
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import com.example.proyectofct.data.retrofit.model.ModeloFactura
import com.example.proyectofct.data.retrofit.network.ApiService
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Mock @Inject constructor(private val retromock: Retromock){
    fun getFacturasMOCK(): ModeloFactura? {
        val response = retromock.create(ApiService::class.java).getFacturasMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<ModeloFactura?> =response.execute()
            return myresponseMock.body()
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }

    fun getDetallesMOCK(): ModeloDetalles? {
        val response = retromock.create(ApiService::class.java).getDetallesMock()
        if (!response.isCanceled) {
            val myresponseMock: Response<ModeloDetalles?> =response.execute()
            return myresponseMock.body()
        } else {
            Log.i("PRUEBA", "NO FUNCNIONA")
        }
        return null
    }
}