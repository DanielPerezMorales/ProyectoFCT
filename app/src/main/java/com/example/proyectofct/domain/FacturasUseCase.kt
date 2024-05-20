package com.example.proyectofct.domain

import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import com.example.proyectofct.data.retrofit.network.FacturaService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FacturasUseCase(private val facturaService: FacturaService) {
    private val roomUseCase=RoomUseCase()
    fun fetchFacturas(appDatabase: FacturaDatabase, callback: (List<FacturaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<FacturaItem>
            val response = facturaService.getFacturas()
            try {
                if (response!!.isNotEmpty()) {
                    facturasList = response
                    roomUseCase.deleteAllFacturasFromRoom(appDatabase)
                    roomUseCase.insertFacturasToRoom(facturasList.map{it.toFacturaEntity()},appDatabase)
                } else {
                    facturasList = appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
                }
            } catch (e: Exception) {
                facturasList = appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
            }
            callback(facturasList)
        }
    }
    operator fun invoke(appDatabase: FacturaDatabase, callback: (List<FacturaItem>) -> Unit){
        fetchFacturas(appDatabase,callback)
    }
}