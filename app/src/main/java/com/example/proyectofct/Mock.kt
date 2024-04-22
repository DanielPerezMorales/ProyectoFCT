package com.example.proyectofct

import android.util.Log
import com.example.proyectofct.core.RetrofitHelper
import com.example.proyectofct.data.model.modelo_Factura
import com.example.proyectofct.data.network.ApiService


class Mock {
    var retromock = RetrofitHelper.getRetrofitMock()

    fun getFacturasMOCK(): modelo_Factura? {
        val response = retromock.create(ApiService::class.java).getFacturasMOCK()
        if (response != null) {
            if (response.isSuccessful) {
                val MyResponse: modelo_Factura? = response.body()
                if (MyResponse != null) {
                    return MyResponse
                }
            } else {
                Log.i("PRUEBA", "NO FUNCNIONA")
            }
        }
        return null
    }
}