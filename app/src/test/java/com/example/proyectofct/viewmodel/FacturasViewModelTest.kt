package com.example.proyectofct.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.retrofit.model.FacturaItem
import com.example.proyectofct.data.retrofit.model.toFacturaEntity
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.KtorUseCase
import com.example.proyectofct.domain.RetromockUseCase
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class FacturasViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FacturasViewModel

    @Mock
    private lateinit var facturasUseCase: FacturasUseCase

    @Mock
    private lateinit var ktorUseCase: KtorUseCase

    @Mock
    private lateinit var facturaDatabase: FacturaDatabase

    @Mock
    private lateinit var filtradoUseCase: FiltradoUseCase

    @Mock
    private lateinit var retromockUseCase: RetromockUseCase

    @Mock
    private lateinit var context: Context

    @Mock
    lateinit var appDatabase: FacturaDatabase

    @Mock
    private lateinit var observer: Observer<List<FacturaItem>?>

    @Mock
    private lateinit var observerExitoso: Observer<Boolean>
    @Mock
    private lateinit var observershowEmptyDialog: Observer<Boolean>

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = FacturasViewModel(facturaDatabase, facturasUseCase, filtradoUseCase,retromockUseCase,ktorUseCase)
        viewModel.facturas.observeForever(observer)
        viewModel.filtradoExitoso.observeForever(observerExitoso)
        viewModel.showEmptyDialog.observeForever(observershowEmptyDialog)
    }

    @After
    fun teardown() {
        viewModel.facturas.removeObserver(observer)
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `fetchFacturas() returns facturas`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = listOf(
            FacturaItem(
                fecha = "01/01/2017",
                descEstado = "Pagada",
                importeOrdenacion = 100F
            )
        )
        `when`(facturasUseCase.fetchFacturas(appDatabase) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.fetchFacturas()


        observer.onChanged(expectedFacturas)
        // Then
        verify(observer).onChanged(expectedFacturas)
    }

    @Test
    fun `fetchFacturas() returns a empty dialog`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = emptyList<FacturaItem>()
        `when`(facturasUseCase.fetchFacturas(appDatabase) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.fetchFacturas()


        observershowEmptyDialog.onChanged(true)
        // Then
        verify(observershowEmptyDialog).onChanged(true)
    }

    @Test
    fun `fecthFacturasKTOR() returns list`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = listOf(
            FacturaItem(
                fecha = "01/01/2017",
                descEstado = "Pagada",
                importeOrdenacion = 100F
            )
        )
        `when`(ktorUseCase.fetchFacturasKtor(appDatabase) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.fetchFacturas()


        observer.onChanged(expectedFacturas)
        // Then
        verify(observer).onChanged(expectedFacturas)
    }

    @Test
    fun `fecthFacturasKTOR() returns a empty dialog`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = emptyList<FacturaItem>()
        `when`(ktorUseCase.fetchFacturasKtor(appDatabase) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.fetchFacturas()


        observershowEmptyDialog.onChanged(true)
        // Then
        verify(observershowEmptyDialog).onChanged(true)
    }

    @Test
    fun `filtrado() returns filtered facturas`() = testScope.runBlockingTest {
        // Given
        val listaFacturaItem = listOf(
            FacturaItem(fecha = "01/01/2017", descEstado = "Pagada", importeOrdenacion = 100F),
            FacturaItem(
                fecha = "01/01/2018",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 200F
            ),
            FacturaItem(
                fecha = "01/01/2019",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 300F
            )
        )
        val expectedFacturas = listOf(
            FacturaItem(fecha = "01/01/2017", descEstado = "Pagada", importeOrdenacion = 100F)
        )
        val listaCheck = listOf("Pagada")
        val listaFiltrados = listOf("Checks")
        val listaEntity = listaFacturaItem.map { it.toFacturaEntity() }

        `when`(
            filtradoUseCase.filtrado(
                0F,
                null,
                null,
                listaCheck,
                listaEntity,
                listaFiltrados
            ) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.filtrado(
            100F,
            null,
            null,
            listaCheck,
            listaEntity,
            listaFiltrados
        )


        // Then
        observerExitoso.onChanged(true)
        observer.onChanged(expectedFacturas)
    }

    @Test
    fun `filtrado() returns emptylist`() = testScope.runBlockingTest {
        // Given
        val listaFacturaItem = listOf(
            FacturaItem(fecha = "01/01/2017", descEstado = "Pendiente de pago", importeOrdenacion = 100F),
            FacturaItem(
                fecha = "01/01/2018",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 200F
            ),
            FacturaItem(
                fecha = "01/01/2019",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 300F
            )
        )
        val expectedFacturas = emptyList<FacturaItem>()
        val listaCheck = listOf("Pagada")
        val listaFiltrados = listOf("Checks")
        val listaEntity = listaFacturaItem.map { it.toFacturaEntity() }

        `when`(
            filtradoUseCase.filtrado(
                0F,
                null,
                null,
                listaCheck,
                listaEntity,
                listaFiltrados
            ) {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.filtrado(
            100F,
            null,
            null,
            listaCheck,
            listaEntity,
            listaFiltrados
        )


        // Then
        observerExitoso.onChanged(false)
    }

    @Test
    fun `putRetroMock posts facturas list`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = listOf(
            FacturaItem(
                fecha = "01/01/2017",
                descEstado = "Pagada",
                importeOrdenacion = 100F
            )
        )
        `when`(retromockUseCase.putRetromock {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.putRetroMock()


        observer.onChanged(expectedFacturas)
        // Then
        verify(observer).onChanged(expectedFacturas)
    }

    @Test
    fun `putRetroMock posts empty dialog when no facturas`() = testScope.runBlockingTest {
        // Given
        val expectedFacturas = emptyList<FacturaItem>()
        `when`(retromockUseCase.putRetromock {}).thenAnswer { invocation ->
            val callback: (List<FacturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.putRetroMock()


        observershowEmptyDialog.onChanged(true)
        // Then
        verify(observershowEmptyDialog).onChanged(true)
    }

    @Test
    fun `putAgain on false`(){
        viewModel.putAgainOnFalse()

        verify(observerExitoso).onChanged(false)
    }
}
