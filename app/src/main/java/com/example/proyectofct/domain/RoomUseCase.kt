package com.example.proyectofct.domain

import com.example.proyectofct.data.retrofit.database.FacturaDatabase
import com.example.proyectofct.data.retrofit.database.entities.FacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomUseCase {
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
}