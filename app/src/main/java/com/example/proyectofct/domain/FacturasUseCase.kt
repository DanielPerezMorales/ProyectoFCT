package com.example.proyectofct.domain

import android.util.Log
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.data.network.FacturaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacturasUseCase(
    private val facturaService: FacturaService
) {
    fun fetchFacturas(appDatabase: FacturaDatabase, callback: (List<facturaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<facturaItem> = listOf()

            val response = facturaService.getFacturas()
            try {
                if (response != null) {
                    facturasList = response.facturas
                    Log.i("TAG", "DATOS INTRODUCIDOS POR API")

                    deleteAllFacturasFromRoom(appDatabase)
                    insertFacturasToRoom(
                        facturasList.map { it.toFacturaEntity() },
                        appDatabase
                    )
                }
            } catch (e: Exception) {
                facturasList =
                    appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
                Log.i("TAG", "DATOS INTRODUCIDOS POR ROOM")
            }
            callback(facturasList)
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


}