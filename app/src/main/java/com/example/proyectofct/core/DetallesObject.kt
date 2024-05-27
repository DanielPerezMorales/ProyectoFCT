package com.example.proyectofct.core

import android.content.Context
import android.util.Log
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetallesObject @Inject constructor(private val servicioMock: Mock) {
    private var modeloDetalles: ModeloDetalles? = null

    fun inicializar() {
        servicioMock
    }

    private suspend fun obtenerDatos(): ModeloDetalles? {
        return withContext(Dispatchers.IO) {
            val facturasMock: ModeloDetalles? = servicioMock.getDetallesMOCK()
            Log.i("kjdekj", "$facturasMock")
            if (facturasMock != null) {
                Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK")
            }
            facturasMock
        }
    }

    suspend fun obtenerInstancia(): ModeloDetalles {
        if (modeloDetalles == null) {
            modeloDetalles = obtenerDatos()
        }
        return modeloDetalles ?: ModeloDetalles("", "", "", "", "")
    }
}
