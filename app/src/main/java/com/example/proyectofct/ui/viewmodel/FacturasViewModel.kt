package com.example.proyectofct.ui.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.ktor.network.KtorServiceClass
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import com.example.proyectofct.data.retrofit.network.FacturaService
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.KtorUseCase
import com.example.proyectofct.domain.RetromockUseCase
import com.example.proyectofct.domain.RoomUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import java.util.logging.Handler

class FacturasViewModel : ViewModel() {
    private val facturaService = FacturaService()
    private val KtorService = KtorServiceClass()
    private val facturasUseCase = FacturasUseCase(facturaService)
    private val filtradoUseCase = FiltradoUseCase()
    private val KtorUseCase = KtorUseCase(KtorService)
    private val RetromockUseCase = RetromockUseCase()
    private val _facturas = MutableLiveData<List<FacturaItem>?>()
    val facturas: MutableLiveData<List<FacturaItem>?> get() = _facturas
    private val _filtradoExitoso = MutableLiveData<Boolean>()

    private val _showEmptyDialog = MutableLiveData<Boolean>()
    val showEmptyDialog: LiveData<Boolean> get() = _showEmptyDialog
    val filtradoExitoso: LiveData<Boolean> get() = _filtradoExitoso
    fun fetchFacturas(appDatabase: FacturaDatabase) {
        _showEmptyDialog.postValue(false)
        facturasUseCase.fetchFacturas(appDatabase) { facturasList ->
            if(facturasList.isEmpty()){
                _showEmptyDialog.postValue(true)
            } else {
                _facturas.postValue(facturasList)
            }
        }
    }

    fun fecthFacturasKTOR(appDatabase: FacturaDatabase){
        _showEmptyDialog.postValue(false)
        KtorUseCase.fetchFacturasKtor(appDatabase){facturasList ->
            if(facturasList.isEmpty()){
                _showEmptyDialog.postValue(true)
            } else {
                _facturas.postValue(facturasList)
            }
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
        filtradoUseCase.filtrado(precio, fechaInicio, fechaFin, listaCheck, lista, listaFiltrados) { filtradoList ->
            if (filtradoList.isEmpty()) {
                _filtradoExitoso.postValue(false)
            } else {
                _filtradoExitoso.postValue(true)
                CoroutineScope(Dispatchers.IO).launch { delay(100) }
                _facturas.postValue(filtradoList)
            }
        }
    }

    fun putRetroMock(context: Context, appDatabase: FacturaDatabase) {
        RetromockUseCase.putRetromock(context, appDatabase){
            if(it.isEmpty()){
                _showEmptyDialog.postValue(true)
            } else {
                _facturas.postValue(it)
            }
        }
    }

    fun putAgainOnFalse(){
        _filtradoExitoso.postValue(false)
    }
}


