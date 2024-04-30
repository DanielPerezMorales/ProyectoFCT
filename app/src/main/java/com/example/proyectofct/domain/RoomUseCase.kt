package com.example.proyectofct.domain

import android.util.Log
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoomUseCase {
    fun insertFacturasToRoom(facturas: List<FacturaEntity>, appDatabase: FacturaDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            //Log.i("ROOM","INSERTADO")
            appDatabase.getFactureDao().insertAll(facturas)
        }
    }

    fun deleteAllFacturasFromRoom(appDatabase: FacturaDatabase) {
        CoroutineScope(Dispatchers.IO).launch {
            //Log.i("ROOM","BORRADO")
            appDatabase.getFactureDao().deleteAllFacturas()
        }
    }
}