package com.example.proyectofct.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.data.Mock
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.data.network.FacturaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class FacturasViewModel : ViewModel() {
    private val facturaService = FacturaService()
    private lateinit var factureServiceMock: Mock
    private val _facturas = MutableLiveData<List<facturaItem>?>()
    val facturas: MutableLiveData<List<facturaItem>?> get() = _facturas
    fun fetchFacturas(appDatabase: FacturaDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<facturaItem> = listOf()
            val response = facturaService.getFacturas()
            if (response != null) {
                facturasList = response.facturas
                Log.i("TAG", "DATOS INTRODUCIDOS POR API")
                deleteAllFacturasFromRoom(appDatabase)
                insertFacturasToRoom(
                    facturasList.map { it.toFacturaEntity() },
                    appDatabase
                )
            } else {
                facturasList =
                    appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
            }

            _facturas.postValue(facturasList)
        }
    }

    fun filtrado(
        precio: Float,
        fechaInicio: Date?,
        fechaFin: Date?,
        listaCheck: List<String>,
        lista: List<FacturaEntity>,
        listaFiltrados: List<String>
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val listaReturn: MutableList<FacturaEntity> = mutableListOf()
            for (i in lista) {
                when (listaFiltrados.size) {
                    1 -> {
                        if (listaFiltrados[0] == "Fechas") {
                            val fechaDentroRango =
                                fechaInicio == null || fechaFin == null || (i.fecha >= fechaInicio && i.fecha <= fechaFin)
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
            _facturas.postValue(listaReturn.toList().map { it.toFacturaItem() })
        }
    }


    fun insertFacturasToRoom(facturas: List<FacturaEntity>, appDatabase: FacturaDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.getFactureDao().insertAll(facturas)
        }
    }

    fun deleteAllFacturasFromRoom(appDatabase: FacturaDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            appDatabase.getFactureDao().deleteAllFacturas()
        }
    }

    fun putRetroMock(context: Context) {
        factureServiceMock= Mock(context)
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<facturaItem> = listOf()
            val facturasMock = factureServiceMock.getFacturasMOCK()
            if (facturasMock != null) {
                facturasList = facturasMock.facturas
                Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK")
            }
            _facturas.postValue(facturasList)
        }
    }
}


