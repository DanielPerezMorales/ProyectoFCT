package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class DetallesUseCaseTest {

    @Mock
    private lateinit var detallesObject: Detalles_Object

    private lateinit var detallesUseCase: DetallesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detallesUseCase = DetallesUseCase(detallesObject)
    }

    @Test
    fun `obtenerDetalles() returns correct Modelo_Detalles`() = runBlocking {
        // Given
        val context: Context = mockk()
        val expectedDetalles = Modelo_Detalles(CAU = "CAU", solicitud = "solicitud", Tipo = "TIpo", Excedentes = "exc", Potencia = "Potence")
        `when`(detallesObject.inicializar(context)).then { }
        `when`(detallesObject.obtenerInstancia()).thenReturn(expectedDetalles)

        // When
        val actualDetalles = detallesUseCase.obtenerDetalles(context)

        // THen
        assertEquals(expectedDetalles, actualDetalles)
    }
}
