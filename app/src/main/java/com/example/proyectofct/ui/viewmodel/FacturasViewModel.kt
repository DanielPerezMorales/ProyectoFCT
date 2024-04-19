package com.example.proyectofct.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.model.FacturaAdapter_RV
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.di.RoomModule
import com.example.proyectofct.ui.view.Activity.Facturas
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacturasViewModel : ViewModel() {
    private val facturaService = FacturaService()
    private val _facturas = MutableLiveData<List<FacturaEntity>?>()
    val facturas: MutableLiveData<List<FacturaEntity>?> get() = _facturas
    //private val appDatabase: FacturaDatabase = RoomModule.provideRoom()

    fun fetchFacturas(room: Boolean) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList :List<facturaItem> = listOf()
            if (room) {
                //appDatabase.getFactureDao().getAllFacturas()
            } else {
                facturasList= facturaService.getFacturas()?.facturas!!
                deleteAllFacturasFromRoom()
                insertFacturasToRoom(facturaService.getFacturas()?.facturas!!.map { it.toFacturaEntity() })
            }
            _facturas.postValue(facturasList.map { it.toFacturaEntity() })
        }
    }

    fun insertFacturasToRoom(facturas: List<FacturaEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            //appDatabase.getFactureDao().insertAll(facturas)
        }
    }

    fun deleteAllFacturasFromRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            //appDatabase.getFactureDao().deleteAllFacturas()
        }
    }
}


