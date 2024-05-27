package com.example.proyectofct.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.KtorUseCase
import com.example.proyectofct.domain.RetromockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class FacturasViewModel @Inject constructor(
    private val facturaDatabase: FacturaDatabase,
    private val facturasUseCase: FacturasUseCase,
    private val filtradoUseCase : FiltradoUseCase,
    private val RetromockUseCase : RetromockUseCase,
    private val KtorUseCase : KtorUseCase
): ViewModel() {
    private val _facturas = MutableLiveData<List<FacturaItem>?>()
    val facturas: MutableLiveData<List<FacturaItem>?> get() = _facturas
    private val _filtradoExitoso = MutableLiveData<Boolean>()

    private val _showEmptyDialog = MutableLiveData<Boolean>()
    val showEmptyDialog: LiveData<Boolean> get() = _showEmptyDialog
    val filtradoExitoso: LiveData<Boolean> get() = _filtradoExitoso
    fun fetchFacturas() {
        _showEmptyDialog.postValue(false)
        facturasUseCase.fetchFacturas(facturaDatabase) { facturasList ->
            if(facturasList.isEmpty()){
                _showEmptyDialog.postValue(true)
            } else {
                _facturas.postValue(facturasList)
            }
        }
    }

    fun fecthFacturasKTOR(){
        _showEmptyDialog.postValue(false)
        KtorUseCase.fetchFacturasKtor(facturaDatabase){facturasList ->
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

    fun putRetroMock() {
        _showEmptyDialog.postValue(false)
        RetromockUseCase.putRetromock(facturaDatabase){
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