package com.example.proyectofct.domain

import android.util.Log
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.model.facturaItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class FiltradoUseCase {
    private val alert = Alert()
    fun filtrado(
        precio: Float,
        fechaInicio: Date?,
        fechaFin: Date?,
        listaCheck: List<String>,
        lista: List<FacturaEntity>,
        listaFiltrados: List<String>,
        callback: (List<facturaItem>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaReturn: MutableList<FacturaEntity> = mutableListOf()
            for (i in lista) {
                when (listaFiltrados.size) {
                    1 -> {
                        if (listaFiltrados[0] == "Fechas") {
                            val fechaDentroRango =
                                if ((i.fecha >= fechaInicio && i.fecha <= fechaFin)) {
                                    true
                                } else if (fechaInicio == null || fechaFin == null) {
                                    false
                                } else {
                                    false
                                }
                            if (precio <= i.precio && fechaDentroRango) {
                                listaReturn.add(i)
                            }
                        } else {
                            if (precio <= i.precio && listaCheck.contains(i.estado)) {
                                listaReturn.add(i)
                            }
                        }
                    }

                    2 -> {
                        val fechaDentroRango =
                            fechaInicio == null || fechaFin == null || (i.fecha >= fechaInicio && i.fecha <= fechaFin)
                        if (precio <= i.precio && fechaDentroRango && listaCheck.contains(i.estado)) {
                            listaReturn.add(i)
                        }
                    }

                    else -> {
                        if (precio <= i.precio) {
                            listaReturn.add(i)
                        }
                    }
                }
            }
            Log.i("TAG","${listaReturn.toList().map { it.toFacturaItem() }}")
            callback(listaReturn.toList().map { it.toFacturaItem() })
        }
    }
}