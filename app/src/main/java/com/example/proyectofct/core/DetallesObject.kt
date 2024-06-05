package com.example.proyectofct.core

import android.content.Context
import android.util.Log
import com.example.proyectofct.R
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetallesObject @Inject constructor(
    private val servicioMock: Mock,
    private val contexto: Context
) {
    private var modeloDetalles: ModeloDetalles? = null

    fun inicializar() {
        servicioMock
    }

    private suspend fun obtenerDatos(): ModeloDetalles? {
        return withContext(Dispatchers.IO) {
            val facturasMock: ModeloDetalles? = servicioMock.getDetallesMOCK()
            facturasMock
        }
    }

    suspend fun obtenerInstancia(): ModeloDetalles {
        if (modeloDetalles == null) {
            modeloDetalles = obtenerDatos()
        }
        return modeloDetalles ?: ModeloDetalles(
            contexto.getString(R.string.espacio_vacio),
            contexto.getString(R.string.espacio_vacio),
            contexto.getString(R.string.espacio_vacio),
            contexto.getString(R.string.espacio_vacio),
            contexto.getString(R.string.espacio_vacio)
        )
    }
}
