package com.example.proyectofct.core

import android.content.Context
import android.util.Log
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.model.Modelo_Detalles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object Detalles_Object {
    private var servicioMock: Mock? = null
    private var modeloDetalles: Modelo_Detalles? = null
    private var inicializado: Boolean = false

    fun inicializar(contexto: Context) {
        if (!inicializado) {
            servicioMock = Mock(contexto)
            inicializado = true
        }
    }

    private suspend fun obtenerDatos(): Modelo_Detalles? {
        return withContext(Dispatchers.IO) {
            val facturasMock: Modelo_Detalles? = servicioMock?.getDetallesMOCK()
            Log.i("kjdekj","$facturasMock")
            if (facturasMock != null) { Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK") }
            facturasMock
        }
    }

    suspend fun obtenerInstancia(): Modelo_Detalles {
        if (modeloDetalles == null) { modeloDetalles = obtenerDatos() }
        return modeloDetalles ?: Modelo_Detalles("", "", "", "", "")
    }
}
