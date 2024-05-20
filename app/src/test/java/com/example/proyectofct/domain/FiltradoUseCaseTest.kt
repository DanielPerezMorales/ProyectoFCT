package com.example.proyectofct.domain

import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.retrofit.model.FacturaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*

class FiltradoUseCaseTest {

    lateinit var filtradoUseCase: FiltradoUseCase

    @Before
    fun setUp() {
        filtradoUseCase = FiltradoUseCase()
    }

    @Test
    fun testFiltrado() = runBlocking {
        // Given
        val precio = 50F
        val fechaInicio: Date? = null
        val fechaFin: Date? = null
        val listaCheck = listOf("Pendiente de pago")
        val listaFiltrados = listOf("Estados")
        val lista = listOf(
            FacturaEntity(1, Date(), "Pagada", 60F),
            FacturaEntity(2, Date(), "Pendiente de pago", 40F),
            FacturaEntity(3, Date(), "Pagada", 100F)
        )
        var resultList: List<FacturaItem>? = null

        // When
        filtradoUseCase.filtrado(precio, fechaInicio, fechaFin, listaCheck, lista, listaFiltrados) {
            resultList = it
        }

        // Then
        delay(100)
        assertEquals(1, resultList?.size)
    }
}
