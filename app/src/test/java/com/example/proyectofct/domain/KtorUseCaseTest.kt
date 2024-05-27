package com.example.proyectofct.domain

import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.ktor.model.factura_item_model
import com.example.proyectofct.data.ktor.model.toFacturaItem
import com.example.proyectofct.data.ktor.network.KtorServiceClass
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class KtorUseCaseTest {

    @Mock
    private lateinit var ktorService: KtorServiceClass
    @Mock
    private lateinit var facturaDatabase: FacturaDatabase
    @Mock
    private lateinit var ktorUseCase: KtorUseCase
    @Mock
    private lateinit var roomUseCase: RoomUseCase
    @Mock
    private lateinit var facturaDao: FacturaDao // Mock the DAO

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        ktorService = mockk()
        facturaDatabase = mockk()
        facturaDao = mockk()
        ktorUseCase = KtorUseCase(ktorService, roomUseCase)
    }


    @Test
    fun `fetchKtor when response is successful`() = runBlocking {
        var response: List<FacturaItem> = listOf()
        val lista: List<factura_item_model> = listOf(
            factura_item_model("Pagada", 100.0F, "07/12/2019"),
            factura_item_model("Pendiente de pago", 50.99F, "21/03/2020")
        )
        // Given
        coEvery { ktorService.getAllFacturas() } returns lista // Mocking null response
        every { facturaDatabase.getFactureDao() } returns facturaDao // Mocking the DAO
        coEvery { facturaDao.getAllFacturas() } returns emptyList()// Mocking null response
        // When
        ktorUseCase.fetchFacturasKtor(facturaDatabase){
            response=it
        }

        // Then
        coVerify(exactly = 1) {
            roomUseCase.insertFacturasToRoom(lista.map { it.toFacturaItem().toFacturaEntity() },facturaDatabase)
        }
        assert(response.map { it.toFacturaEntity() } == lista.map { it.toFacturaItem().toFacturaEntity() })

    }

    @Test
    fun `fetchKtor when response is not successful`() = runBlocking {
        var response: List<FacturaItem> = listOf()
        val lista: List<factura_item_model> = listOf(
        )
        // Given
        coEvery { ktorService.getAllFacturas() } returns emptyList() // Mocking null response
        every { facturaDatabase.getFactureDao() } returns facturaDao // Mocking the DAO
        coEvery { facturaDao.getAllFacturas() } returns emptyList()// Mocking null response
        // When
        ktorUseCase.fetchFacturasKtor(facturaDatabase){
            response=it
        }

        // Then
        coVerify(exactly = 1) {
            facturaDao.insertAll(lista.map { it.toFacturaItem().toFacturaEntity() })
        }

    }
}
