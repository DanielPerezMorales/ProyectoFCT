package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.model.ModeloDetalles

class DetallesUseCase (private val detalles:DetallesObject){
    suspend fun obtenerDetalles(context: Context): ModeloDetalles {
        detalles.inicializar(context)
        return detalles.obtenerInstancia()
    }
}
