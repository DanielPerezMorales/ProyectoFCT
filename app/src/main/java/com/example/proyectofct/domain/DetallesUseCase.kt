package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import javax.inject.Inject

class DetallesUseCase @Inject constructor(private val detalles:DetallesObject){
    suspend fun obtenerDetalles() : ModeloDetalles {
        detalles.inicializar()
        return detalles.obtenerInstancia()
    }
}
