package com.example.proyectofct.domain

import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.ModeloFactura
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RetromockUseCaseTest {

    @org.mockito.Mock
    private lateinit var mock: Mock

    @org.mockito.Mock
    private lateinit var roomUseCase: RoomUseCase

    private lateinit var retromockUseCase: RetromockUseCase

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        retromockUseCase = RetromockUseCase(roomUseCase, mock)
    }

    @Test
    fun `putRetromock should call deleteAllFacturasFromRoom and insertFacturasToRoom when facturasMock is not null`() =
        runTest(testDispatcher) {
            var expectedResponse = emptyList<FacturaItem>()
            val facturasMock = ModeloFactura(
                "2", listOf(
                    FacturaItem("Pagada", 100.0F, "07/12/2019"),
                    FacturaItem("Pendiente de pago", 50.99F, "21/03/2020")
                )
            )

            `when`(mock.getFacturasMOCK()).thenReturn(facturasMock)

            retromockUseCase.putRetromock {
                expectedResponse = it
            }

            verify(roomUseCase).deleteAllFacturasFromRoom()
            verify(roomUseCase).insertFacturasToRoom(
                facturasMock.facturas.map { it.toFacturaEntity() }
            )

            assertEquals(facturasMock.facturas, expectedResponse)
        }

    @Test
    fun `putRetromock should call callback with empty list when facturasMock is null`() =
        runTest(testDispatcher) {
            var expectedResponse = listOf<FacturaItem>()

            `when`(mock.getFacturasMOCK()).thenReturn(null)

            retromockUseCase.putRetromock {
                expectedResponse = it
            }

            assertEquals(emptyList<FacturaItem>(), expectedResponse)
        }
}

