package com.example.proyectofct.domain

import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.ktor.model.toFacturaItem
import com.example.proyectofct.data.ktor.network.KtorServiceClass
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class KtorUseCase(private val KtorService: KtorServiceClass) {
    private val roomUseCase = RoomUseCase()
    fun fetchFacturasKtor(appDatabase: FacturaDatabase, callback: (List<FacturaItem>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            var lista = KtorService.getAllFacturas()?.map { it.toFacturaItem() }
            try {
                if (lista != null) {
                    roomUseCase.deleteAllFacturasFromRoom(appDatabase)
                    roomUseCase.insertFacturasToRoom(
                        lista.map { it.toFacturaEntity() },
                        appDatabase
                    )
                } else {
                    lista = emptyList()
                }
            } catch (e: Exception) {
                lista = appDatabase.getFactureDao().getAllFacturas().map { it.toFacturaItem() }
            }
            if (lista != null) {
                callback(lista)
            }
        }
    }
}