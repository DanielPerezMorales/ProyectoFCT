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
    private val RoomUseCase=RoomUseCase()
    fun fetchFacturas(appDatabase: FacturaDatabase, callback: (List<facturaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<facturaItem> = listOf()

            val response = facturaService.getFacturas()
            try {
                if (response!!.isNotEmpty()) {
                    facturasList = response
                    //Log.i("TAG", "DATOS INTRODUCIDOS POR API")

                    RoomUseCase.deleteAllFacturasFromRoom(appDatabase)
                    RoomUseCase.insertFacturasToRoom(facturasList.map{it.toFacturaEntity()},appDatabase)
                } else {
                    facturasList =
                        appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
                    //Log.i("TAG", "DATOS INTRODUCIDOS POR ROOM")
                }
            } catch (e: Exception) {
                facturasList =
                    appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
                //Log.i("TAG", "DATOS INTRODUCIDOS POR ROOM")
            }
            callback(facturasList)
        }
    }
    operator fun invoke(appDatabase: FacturaDatabase, callback: (List<facturaItem>) -> Unit){
        fetchFacturas(appDatabase,callback)
    }
}