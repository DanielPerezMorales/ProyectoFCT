package com.example.proyectofct.domain

import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class RetromockUseCase@Inject constructor(
    private val roomUseCase: RoomUseCase,
    private val mockFactory: Mock
) {
    fun putRetromock(
        callback: (List<FacturaItem>) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            var facturasList: List<FacturaItem> = listOf()
            val facturasMock = mockFactory.getFacturasMOCK()
            if (facturasMock != null) {
                facturasList = facturasMock.facturas
                roomUseCase.deleteAllFacturasFromRoom()
                roomUseCase.insertFacturasToRoom(
                    facturasList.map { it.toFacturaEntity() }
                )
            }
            callback(facturasList)
        }
    }
}