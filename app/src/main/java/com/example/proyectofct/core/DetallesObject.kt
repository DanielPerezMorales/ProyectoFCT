package com.example.proyectofct.core

import android.content.Context
import android.util.Log
import com.example.proyectofct.data.retrofit.mock.Mock
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object DetallesObject {
    private var servicioMock: Mock? = null
    private var modeloDetalles: ModeloDetalles? = null
    private var inicializado: Boolean = false

    fun inicializar(contexto: Context) {
        if (!inicializado) {
            servicioMock = Mock(contexto)
            inicializado = true
        }
    }

    private suspend fun obtenerDatos(): ModeloDetalles? {
        return withContext(Dispatchers.IO) {
            val facturasMock: ModeloDetalles? = servicioMock?.getDetallesMOCK()
            Log.i("kjdekj","$facturasMock")
            if (facturasMock != null) { Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK") }
            facturasMock
        }
    }

    suspend fun obtenerInstancia(): ModeloDetalles {
        if (modeloDetalles == null) { modeloDetalles = obtenerDatos() }
        return modeloDetalles ?: ModeloDetalles("", "", "", "", "")
    }
}
