package com.example.proyectofct.domain

import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RoomUseCase @Inject constructor(private val facturaDatabase: FacturaDatabase){
    fun insertFacturasToRoom(facturas: List<FacturaEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            facturaDatabase.getFactureDao().insertAll(facturas)
        }
    }

    fun deleteAllFacturasFromRoom() {
        CoroutineScope(Dispatchers.IO).launch {
            facturaDatabase.getFactureDao().deleteAllFacturas()
        }
    }
}