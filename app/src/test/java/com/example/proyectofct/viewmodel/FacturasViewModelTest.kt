package com.example.proyectofct.viewmodel

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.modelo_Factura
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.domain.FacturasUseCase
import com.example.proyectofct.domain.FiltradoUseCase
import com.example.proyectofct.domain.RoomUseCase
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.Date

@ExperimentalCoroutinesApi
class FacturasViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: FacturasViewModel

    @Mock
    private lateinit var facturasUseCase: FacturasUseCase

    @Mock
    private lateinit var filtradoUseCase: FiltradoUseCase

    @Mock
    private lateinit var context: Context

    @Mock
    private lateinit var alert: Alert

    @Mock
    lateinit var appDatabase: FacturaDatabase

    @Mock
    lateinit var factureServiceMock: com.example.proyectofct.data.mock.Mock

    @Mock
    lateinit var roomUseCase: RoomUseCase

    @Mock
    private lateinit var observer: Observer<List<facturaItem>?>

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel = FacturasViewModel()
        viewModel.facturas.observeForever(observer)
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
            facturaItem(
                fecha = "01/01/2017",
                descEstado = "Pagada",
                importeOrdenacion = 100F
            )
        )
        `when`(facturasUseCase.fetchFacturas(appDatabase) {}).thenAnswer { invocation ->
            val callback: (List<facturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.fetchFacturas(appDatabase)


        observer.onChanged(expectedFacturas)
        // Then
        verify(observer).onChanged(expectedFacturas)
    }

    @Test
    fun `filtrado() returns filtered facturas`() = testScope.runBlockingTest {
        // Given
        val listaFacturaItem = listOf(
            facturaItem(fecha = "01/01/2017", descEstado = "Pagada", importeOrdenacion = 100F),
            facturaItem(
                fecha = "01/01/2018",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 200F
            ),
            facturaItem(
                fecha = "01/01/2019",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 300F
            )
        )
        val expectedFacturas = listOf(
            facturaItem(fecha = "01/01/2017", descEstado = "Pagada", importeOrdenacion = 100F)
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
            val callback: (List<facturaItem>?) -> Unit = invocation.getArgument(1)
            callback(expectedFacturas)
        }

        // When
        viewModel.filtrado(
            100F,
            null,
            null,
            listaCheck,
            listaEntity,
            context,
            listaFiltrados
        )


        // Then
        verify(observer).onChanged(expectedFacturas)
        //verify(alert).showAlert("ERROR", "No hay facturas que cumplan estos requisitos", context)
    }

    @Test
    fun `putRetroMock posts facturas list`() = runBlockingTest {
        // Given
        val expectedfacturasList :List<facturaItem> = listOf(
            facturaItem(fecha = "01/01/2017", descEstado = "Pagada", importeOrdenacion = 100F),
            facturaItem(
                fecha = "01/01/2018",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 200F
            ),
            facturaItem(
                fecha = "01/01/2019",
                descEstado = "Pendiente de pago",
                importeOrdenacion = 300F
            )
        )
        val lista: List<facturaItem>? = factureServiceMock.getFacturasMOCK()?.facturas
        if(lista != null){
            `when`(lista).thenReturn(expectedfacturasList)
        }

        // When
        viewModel.putRetroMock(context, appDatabase)

        observer.onChanged(expectedfacturasList)

        // Then
        verify(observer).onChanged(expectedfacturasList)
    }
}
