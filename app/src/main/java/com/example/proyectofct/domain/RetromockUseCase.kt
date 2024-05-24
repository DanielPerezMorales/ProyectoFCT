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

class RetromockUseCase(
    private val roomUseCase: RoomUseCase = RoomUseCase(),
    private val mockFactory: (Context) -> Mock = { Mock(it) }
) {
    fun putRetromock(
        context: Context,
        appDatabase: FacturaDatabase,
        callback: (List<FacturaItem>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val mock = mockFactory(context)
            var facturasList: List<FacturaItem> = listOf()
            val facturasMock = mock.getFacturasMOCK()
            if (facturasMock != null) {
                facturasList = facturasMock.facturas
                roomUseCase.deleteAllFacturasFromRoom(appDatabase)
                roomUseCase.insertFacturasToRoom(
                    facturasList.map { it.toFacturaEntity() },
                    appDatabase
                )
            }
            callback(facturasList)
        }
    }
}