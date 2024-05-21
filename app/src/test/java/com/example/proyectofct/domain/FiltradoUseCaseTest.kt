package com.example.proyectofct.domain

import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.retrofit.model.FacturaItem
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class FiltradoUseCaseTest {

    lateinit var filtradoUseCase: FiltradoUseCase

    @Before
    fun setUp() {
        filtradoUseCase = FiltradoUseCase()
    }

    @Test
    fun `testFiltrado when only filter with checks and price`() = runBlocking {
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

    @Test
    fun `testFiltrado when only filter with fecha and price`() = runBlocking {
        // Given
        val precio = 60F
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaInicio = "20/01/2020"
        val fechaFin: String = "20/05/2020"
        val fechaInicioDate = formatoFecha.parse(fechaInicio)
        val fechaFinDate = formatoFecha.parse(fechaFin)
        val listaCheck = listOf("")
        val listaFiltrados = listOf("Fechas")
        val lista = listOf(
            FacturaEntity(1, fechaInicioDate!!, "Pagada", 60F),
            FacturaEntity(2, Date(), "Pendiente de pago", 40F),
            FacturaEntity(3, Date(), "Pagada", 100F)
        )
        var resultList: List<FacturaItem>? = null

        // When
        filtradoUseCase.filtrado(precio, fechaInicioDate, fechaFinDate, listaCheck, lista, listaFiltrados) {
            resultList = it
        }

        // Then
        delay(100)
        assertEquals(1, resultList?.size)
    }

    @Test
    fun `testFiltrado when only filter with price`() = runBlocking {
        // Given
        val precio = 60F
        val fechaInicio: Date? = null
        val fechaFin: Date? = null
        val listaCheck = emptyList<String>()
        val listaFiltrados = emptyList<String>()
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
        assertEquals(2, resultList?.size)
    }

    @Test
    fun `testFiltrado when filter with all`() = runBlocking {
        // Given
        val precio = 60F
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaInicio = "20/01/2020"
        val fechaFin = "20/05/2020"
        val fechaInicioDate = formatoFecha.parse(fechaInicio)
        val fechaFinDate = formatoFecha.parse(fechaFin)
        val listaCheck = listOf("Pagada")
        val listaFiltrados = listOf("Fechas", "Checks")
        val lista = listOf(
            FacturaEntity(1, fechaInicioDate!!, "Pagada", 60F),
            FacturaEntity(2, fechaInicioDate, "Pendiente de pago", 40F),
            FacturaEntity(3, Date(), "Pagada", 100F)
        )
        var resultList: List<FacturaItem>? = null

        // When
        filtradoUseCase.filtrado(precio, fechaInicioDate, fechaFinDate, listaCheck, lista, listaFiltrados) {
            resultList = it
        }

        // Then
        delay(100)
        assertEquals(1, resultList?.size)
    }

    @Test
    fun `testFiltrado returns all because no filter`() = runBlocking {
        // Given
        val precio = 0.0F
        val fechaInicio: Date? = null
        val fechaFin: Date? = null
        val listaCheck = emptyList<String>()
        val listaFiltrados = emptyList<String>()
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
        assertEquals(3, resultList?.size)
    }

    @Test
    fun `testFiltradowhen when only filter with fecha`() = runBlocking {
        // Given
        val precio = 0.0F
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaInicio = "20/01/2020"
        val fechaFin = "20/05/2020"
        val fechaInicioDate = formatoFecha.parse(fechaInicio)
        val fechaFinDate = formatoFecha.parse(fechaFin)
        val listaCheck = emptyList<String>()
        val listaFiltrados = listOf("Fechas")
        val lista = listOf(
            FacturaEntity(1, fechaInicioDate!!, "Pagada", 60F),
            FacturaEntity(2, Date(), "Pendiente de pago", 40F),
            FacturaEntity(3, Date(), "Pagada", 100F)
        )
        var resultList: List<FacturaItem>? = null

        // When
        filtradoUseCase.filtrado(precio, fechaInicioDate, fechaFinDate, listaCheck, lista, listaFiltrados) {
            resultList = it
        }

        // Then
        delay(100)
        assertEquals(1, resultList?.size)
    }

    @Test
    fun `testFiltrado when only filter with checks`() = runBlocking {
        // Given
        val precio = 0.0F
        val fechaInicio: Date? = null
        val fechaFin: Date? = null
        val listaCheck = listOf("Pagada")
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
        assertEquals(2, resultList?.size)
    }

    @Test
    fun `testFiltrado when only filter with checks and fecha`() = runBlocking {
        // Given
        val precio = 0.0F
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaInicio = "20/01/2020"
        val fechaFin = "20/05/2020"
        val fechaInicioDate = formatoFecha.parse(fechaInicio)
        val fechaFinDate = formatoFecha.parse(fechaFin)
        val listaCheck = listOf("Pagada")
        val listaFiltrados = listOf("Fechas", "Checks")
        val lista = listOf(
            FacturaEntity(1, fechaInicioDate!!, "Pagada", 60F),
            FacturaEntity(2, Date(), "Pendiente de pago", 40F),
            FacturaEntity(3, Date(), "Pagada", 100F)
        )
        var resultList: List<FacturaItem>? = null

        // When
        filtradoUseCase.filtrado(precio, fechaInicioDate, fechaFinDate, listaCheck, lista, listaFiltrados) {
            resultList = it
        }

        // Then
        delay(100)
        assertEquals(1, resultList?.size)
    }
}
