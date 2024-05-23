package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.dao.FacturaDao
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.ModeloFactura
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RetromockUseCaseTest {

    private lateinit var retromockUseCase: RetromockUseCase

    @Mock
    private lateinit var facturaDao: FacturaDao

    @Mock
    private lateinit var facturaDatabase: FacturaDatabase

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var factureServiceMock: com.example.proyectofct.data.mock.Mock

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        facturaDao = mockk(relaxed = true)
        facturaDatabase = mockk(relaxed = true)
        context = mockk(relaxed = true)
        factureServiceMock = mockk(relaxed = true)

        retromockUseCase = RetromockUseCase()

        // Mock the database to return the DAO
        every { facturaDatabase.getFactureDao() } returns facturaDao
    }

    @Test
    fun `retromock test is successful`() = runBlocking {
        val lista = ModeloFactura(
            numFacturas = "2",
            facturas = listOf(
                FacturaItem("Pagada", 100.0F, "07/12/2019"),
                FacturaItem("Pendiente de pago", 50.99F, "21/03/2020")
            )
        )

        // Mock the response of the mock service
        every { factureServiceMock.getFacturasMOCK() } returns lista

        var facturasList: List<FacturaItem> = emptyList()

        // Use runBlocking to wait for the coroutine to complete
        withContext(Dispatchers.IO) {
            retromockUseCase.putRetromock(context, appDatabase = facturaDatabase) { facturaItems ->
                facturasList = facturaItems
            }
        }

        // Verify the interaction with the DAO
        coVerify(exactly = 1) { facturaDao.insertAll(facturasList.map { it.toFacturaEntity() }) }
        assert(facturasList == lista.facturas)
    }
}
