package com.example.proyectofct.domain

import android.content.Context
import android.util.Log
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RetromockUseCase {
    private val roomUseCase=RoomUseCase()
    fun putRetromock(context: Context, appDatabase: FacturaDatabase, callback: (List<FacturaItem>) -> Unit){
        val factureServiceMock = Mock(context)
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<FacturaItem> = listOf()
            val facturasMock = factureServiceMock.getFacturasMOCK()
            if (facturasMock != null) {
                facturasList = facturasMock.facturas
                roomUseCase.deleteAllFacturasFromRoom(appDatabase)
                roomUseCase.insertFacturasToRoom(facturasList.map { it.toFacturaEntity() }, appDatabase)
                Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK")
            }
            callback(facturasList)
        }
    }

}