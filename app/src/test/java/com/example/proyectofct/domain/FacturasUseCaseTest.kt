package com.example.proyectofct.domain

import org.mockito.Mock
import com.example.proyectofct.data.retrofit.database.FacturaDatabase
import com.example.proyectofct.data.retrofit.database.dao.FacturaDao
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import com.example.proyectofct.data.retrofit.network.FacturaService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations

class FacturasUseCaseTest {

    @Mock
    private lateinit var facturaService: FacturaService
    @Mock
    private lateinit var facturaDatabase: FacturaDatabase
    @Mock
    private lateinit var facturaDao: FacturaDao // Mock the DAO

    lateinit var facturasUseCase: FacturasUseCase

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        facturaService= mockk()
        facturaDatabase= mockk()
        facturaDao= mockk()
        facturasUseCase= FacturasUseCase(facturaService)
    }

    @Test
    fun `fetchFacturas when response is not successful`() = runBlocking {
        var lista: List<FacturaItem> = listOf()
        // Given
        coEvery { facturaService.getFacturas() } returns emptyList() // Mocking null response
        every { facturaDatabase.getFactureDao() } returns facturaDao // Mocking the DAO
        coEvery { facturaDao.getAllFacturas() } returns emptyList()// Mocking null response

        // When
        facturasUseCase(facturaDatabase){
            lista=it
        }

        // Then
        coVerify(exactly = 1) { facturaDao.getAllFacturas() }

    }

    @Test
    fun `fetchFacturas when response is successful`() = runBlocking {
        var response:List<FacturaItem> = listOf()
        var lista: List<FacturaItem> = listOf(FacturaItem("Pagada", 100.0F, "07/12/2019"), FacturaItem("Pendiente de pago",50.99F, "21/03/2020"))
        // Given
        coEvery { facturaService.getFacturas() } returns lista // Mocking null response
        every { facturaDatabase.getFactureDao() } returns facturaDao // Mocking the DAO
        coEvery { facturaDao.getAllFacturas() } returns emptyList()// Mocking null response

        // When
        facturasUseCase(facturaDatabase){
            response=it
        }

        // Then
        //coVerify(exactly = 0) { facturaDao.deleteAllFacturas() }
        coVerify(exactly = 1) { facturaDao.insertAll(lista.map { it.toFacturaEntity() }) }
        assert(response==lista)

    }

}
