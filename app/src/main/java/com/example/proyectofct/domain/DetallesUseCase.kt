package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles

class DetallesUseCase (private val detalles:Detalles_Object){
    suspend fun obtenerDetalles(context: Context): Modelo_Detalles {
        detalles.inicializar(context)
        return detalles.obtenerInstancia()
    }
}
