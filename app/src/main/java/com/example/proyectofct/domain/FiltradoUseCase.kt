package com.example.proyectofct.domain

import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.retrofit.model.FacturaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

class FiltradoUseCase @Inject constructor(){
    fun filtrado(precio: Float, fechaInicio: Date?, fechaFin: Date?, listaCheck: List<String>, lista: List<FacturaEntity>, listaFiltrados: List<String>, callback: (List<FacturaItem>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaReturn: MutableList<FacturaEntity> = mutableListOf()
            for (i in lista) {
                when (listaFiltrados.size) {
                    1 -> {
                        if (listaFiltrados[0] == "Fechas") {
                            val fechaDentroRango = if ((i.fecha >= fechaInicio && i.fecha <= fechaFin)) {
                                    true
                                } else { false }

                            if (precio != 0.0F) {
                                if (precio >= i.precio && fechaDentroRango) { listaReturn.add(i) }
                            } else {
                                if (fechaDentroRango) { listaReturn.add(i) }
                            }
                        } else {
                            if (precio != 0.0F) {
                                if (precio >= i.precio && listaCheck.contains(i.estado)) { listaReturn.add(i) }
                            } else {
                                if (listaCheck.contains(i.estado)) { listaReturn.add(i) }
                            }
                        }
                    }

                    2 -> {
                        val fechaDentroRango = fechaInicio == null || fechaFin == null || (i.fecha >= fechaInicio && i.fecha <= fechaFin)
                        if (precio != 0.0F) {
                            if (precio>=i.precio && fechaDentroRango && listaCheck.contains(i.estado)) { listaReturn.add(i) }
                        } else {
                            if (fechaDentroRango && listaCheck.contains(i.estado)) { listaReturn.add(i) }
                        }
                    }

                    else -> {
                        if (precio != 0.0F) {
                            if (precio>=i.precio) { listaReturn.add(i) }
                        } else { listaReturn.add(i) }
                    }
                }
            }
            val listaFiltradaItems = listaReturn.toList().map { it.toFacturaItem() }
            callback(listaFiltradaItems)
        }
    }
}