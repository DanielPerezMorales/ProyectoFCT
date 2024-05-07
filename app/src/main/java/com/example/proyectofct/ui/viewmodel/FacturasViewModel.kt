package com.example.proyectofct.ui.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.RoomUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date

class FacturasViewModel : ViewModel() {
    private val facturaService = FacturaService()
    private val facturasUseCase = FacturasUseCase(facturaService)
    private val filtradoUseCase = FiltradoUseCase()
    private lateinit var factureServiceMock: Mock
    var alert = Alert()
    private val RoomUseCase = RoomUseCase()
    private val _facturas = MutableLiveData<List<facturaItem>?>()
    val facturas: MutableLiveData<List<facturaItem>?> get() = _facturas

    private val _filtradoExitoso = MutableLiveData<Boolean>()
    val filtradoExitoso: LiveData<Boolean> get() = _filtradoExitoso
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
        context: Context,
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
            if (filtradoList.isEmpty()) {
                _filtradoExitoso.postValue(false)
            } else {
                _filtradoExitoso.postValue(true)
                CoroutineScope(Dispatchers.IO).launch {
                    delay(100)
                }
                _facturas.postValue(filtradoList)
            }
            _filtradoExitoso.postValue(false)
        }
    }

    fun putRetroMock(context: Context, appDatabase: FacturaDatabase) {
        factureServiceMock = Mock(context)
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<facturaItem> = listOf()
            val facturasMock = factureServiceMock.getFacturasMOCK()
            if (facturasMock != null) {
                facturasList = facturasMock.facturas
                RoomUseCase.deleteAllFacturasFromRoom(appDatabase)
                RoomUseCase.insertFacturasToRoom(
                    facturasList.map { it.toFacturaEntity() },
                    appDatabase
                )
                Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK")

            }
            _facturas.postValue(facturasList)
        }
    }

    fun putAgainFalse(){
        _filtradoExitoso.postValue(false)
    }
}


