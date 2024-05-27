package com.example.proyectofct.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
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
    private lateinit var detallesObserver: Observer<ModeloDetalles>

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        detallesViewModel = DetallesViewModel(detallesUseCase)
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
        val expectedDetalles = ModeloDetalles(cau = "CAU", solicitud = "solicitud", tipo = "TIpo", excedentes = "exc", potencia = "Potence")
        `when`(detallesUseCase.obtenerDetalles()).thenReturn(expectedDetalles)

        // When
        detallesViewModel.cargarDetalles()

        delay(100)


        // Then
        verify(detallesObserver).onChanged(expectedDetalles)
    }
}
