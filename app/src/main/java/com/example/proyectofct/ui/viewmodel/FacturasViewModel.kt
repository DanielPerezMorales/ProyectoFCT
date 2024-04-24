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
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class FacturasViewModel : ViewModel() {
    private val facturaService = FacturaService()
    private val facturasUseCase = FacturasUseCase(facturaService)
    private val filtradoUseCase = FiltradoUseCase()
    private lateinit var factureServiceMock: Mock
    private val _facturas = MutableLiveData<List<facturaItem>?>()
    val facturas: MutableLiveData<List<facturaItem>?> get() = _facturas
    fun fetchFacturas(appDatabase: FacturaDatabase) {
        facturasUseCase.fetchFacturas(appDatabase) { facturasList ->
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
        filtradoUseCase.filtrado(
            precio,
            fechaInicio,
            fechaFin,
            listaCheck,
            lista,
            listaFiltrados
        ) { filtradoList ->
            _facturas.postValue(filtradoList)
        }
    }

    fun putRetroMock(context: Context) {
        factureServiceMock = Mock(context)
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


