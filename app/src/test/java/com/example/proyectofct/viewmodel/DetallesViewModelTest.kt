package com.example.proyectofct.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.domain.DetallesUseCase
import com.example.proyectofct.ui.viewmodel.DetallesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class DetallesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var detallesViewModel: DetallesViewModel

    @Mock
    private lateinit var detallesUseCase: DetallesUseCase

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var detallesObject: Detalles_Object

    @Mock
    private lateinit var detallesObserver: Observer<Modelo_Detalles>

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        detallesViewModel = DetallesViewModel()
        detallesViewModel.detallesLiveData.observeForever(detallesObserver)
    }

    @After
    fun teardown() {
        detallesViewModel.detallesLiveData.removeObserver(detallesObserver)
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `cargarDetalles() returns correct data`() = testScope.runBlockingTest {
        // Given
        val expectedDetalles = Modelo_Detalles(CAU = "CAU", solicitud = "solicitud", Tipo = "TIpo", Excedentes = "exc", Potencia = "Potence")
        `when`(detallesUseCase.obtenerDetalles(context)).thenReturn(expectedDetalles)

        // When
        detallesViewModel.cargarDetalles(context, detallesObject)

        delay(100)
        detallesObserver.onChanged(expectedDetalles)


        // Then
        verify(detallesObserver).onChanged(expectedDetalles)
    }
}
