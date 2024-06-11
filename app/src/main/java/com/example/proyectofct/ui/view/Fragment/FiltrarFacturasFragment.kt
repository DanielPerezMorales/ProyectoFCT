package com.example.proyectofct.ui.view.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.DatePickerFragment
import com.example.proyectofct.data.database.FacturaDatabase
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import javax.inject.Inject

@AndroidEntryPoint
class FiltrarFacturasFragment : Fragment() {
    private var precio: Float = 0.0f

    @Inject
    lateinit var facturaDatabase: FacturaDatabase
    private val facturaViewModel: FacturasViewModel by activityViewModels()
    private lateinit var binding: FragmentFiltrarFacturasBinding
    private var filtradoRealizado = false

    @Inject
    lateinit var alert: Alert

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFiltrarFacturasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibCloseWindow.setOnClickListener {
            closeFilter()
        }

        CoroutineScope(Dispatchers.IO).launch { putValues() }

        binding.volumeRange.addOnChangeListener { _, value, _ ->
            putMaxSeleccionadoAndPrecio(value)
        }

        selectDate()
        binding.btnEliminarFiltro.setOnClickListener {
            alert.showAlertYesOrNo(
                getString(R.string.Filtrado_borrado),
                getString(R.string.Filtrado_seguro_que_quieres_borrar_el_filtrado),
                requireContext(), yesAction = { delete() }, noAction = {})
        }
        binding.btnAplicar.setOnClickListener {
            val dates = checkDates()
            if (dates) {
                CoroutineScope(Dispatchers.IO).launch { apply(value = precio) }
            }
        }
        comprobar()
        checkStats()
    }

    @SuppressLint("SetTextI18n")
    private suspend fun putValues() {
        val max: Float? = putMaxValue(
            facturaDatabase.getFactureDao().getAllFacturas()
        )
        if (max != null) {
            binding.TVMaxPrecio.text = max.toString()
        } else {
            binding.TVMaxPrecio.text = getString(R.string.Filtrado_100)
            binding.TVMinPrecio.text = getString(R.string.Filtrado_cero)
        }

        if (max != null) {
            binding.volumeRange.valueTo = (max.toInt() + 1).toFloat()
            binding.volumeRange.stepSize = 1F
        }
    }

    @SuppressLint("SetTextI18n")
    private fun putMaxSeleccionadoAndPrecio(value: Float) {
        binding.TVMaxPrecioSeleccionado.text = getString(R.string.Filtrado_parameter, value.toString())
        precio = value
    }

    private fun comprobar() {
        facturaViewModel.filtradoExitoso.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                closeFilter()
                facturaViewModel.putAgainOnFalse()
                filtradoRealizado = false
            } else {
                if (filtradoRealizado) {
                    alert.showAlert(
                        getString(R.string.error),
                        getString(R.string.Filtrado_no_hay_facturas_que_cumplan_estos_requisitos),
                        requireContext()
                    )
                    delete()
                }
                filtradoRealizado = false
            }
        }
    }

    private fun closeFilter() {
        saveFilterStats()
        requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
        val vp = requireActivity().findViewById<ViewPager>(R.id.VP)
        vp.visibility = View.GONE
    }

    private fun putMaxValue(lista: List<FacturaEntity>): Float? {
        var max: Float? = null
        for (i in lista) {
            if (max != null) {
                if (max <= i.precio) {
                    max = i.precio
                }
            } else {
                max = i.precio
            }
        }
        return max
    }

    private fun selectDate() {
        var fechaDesde: DatePickerFragment?
        var fechaHasta: DatePickerFragment?

        binding.btnCalendarDesde.setOnClickListener {
            fechaDesde = DatePickerFragment { year, month, day ->
                mostrarResultado(year, month, day, getString(R.string.Filtrado_desde))
            }
            fechaDesde!!.show(childFragmentManager, getString(R.string.Filtrado_date_picker))
        }

        binding.btnCalendarHasta.setOnClickListener {
            fechaHasta = DatePickerFragment { year, month, day ->
                mostrarResultado(year, month, day, getString(R.string.Filtrado_hasta))
            }
            fechaHasta!!.show(childFragmentManager, getString(R.string.Filtrado_date_picker))
        }
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarResultado(year: Int, month: Int, day: Int, boton: String) {
        if (boton == getString(R.string.Filtrado_desde)) {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarDesde.text = getString(
                        R.string.Filtrado_fecha1,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                } else {
                    binding.btnCalendarDesde.text = getString(
                        R.string.Filtrado_fecha2,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarDesde.text = getString(
                        R.string.Filtrado_fecha3,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                } else {
                    binding.btnCalendarDesde.text = getString(
                        R.string.Filtrado_fecha4,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                }
            }
        } else {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarHasta.text = getString(
                        R.string.Filtrado_fecha1,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                } else {
                    binding.btnCalendarHasta.text = getString(
                        R.string.Filtrado_fecha2,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarHasta.text = getString(
                        R.string.Filtrado_fecha3,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                } else {
                    binding.btnCalendarHasta.text = getString(
                        R.string.Filtrado_fecha4,
                        day.toString(),
                        (month + 1).toString(),
                        year.toString()
                    )
                }
            }
        }
    }

    private fun delete() {
        binding.btnCalendarDesde.setText(R.string.Filtrado_dia_mes_anio)
        binding.btnCalendarHasta.setText(R.string.Filtrado_dia_mes_anio)
        binding.volumeRange.setValues(0F)
        binding.ChckPagadas.isChecked = false
        binding.ChckPendientesDePago.isChecked = false
        binding.ChckAnuladas.isChecked = false
        binding.ChckCuotaFija.isChecked = false
        binding.ChckPlanDePago.isChecked = false
    }

    @SuppressLint("SimpleDateFormat")
    private suspend fun apply(value: Float) {
        filtradoRealizado = true
        if (isAdded && activity != null) {
            val lista: List<FacturaEntity> =
                facturaDatabase.getFactureDao().getAllFacturas()
            val formatoFecha = SimpleDateFormat(getString(R.string.Core_fecha_filtro))
            val fechaInicioText = binding.btnCalendarDesde.text.toString()
            val fechaFinText = binding.btnCalendarHasta.text.toString()
            val listaCheck: MutableList<String> = checkBox()
            val fechaInicio =
                if (fechaInicioText != getString(R.string.Filtrado_dia_mes_anio)) formatoFecha.parse(
                    fechaInicioText
                ) else null
            val fechaFin =
                if (fechaFinText != getString(R.string.Filtrado_dia_mes_anio)) formatoFecha.parse(
                    fechaFinText
                ) else null
            facturaViewModel.filtrado(
                precio = value,
                fechaInicio = fechaInicio,
                fechaFin = fechaFin,
                listaCheck = listaCheck,
                lista,
                listadoFiltrado()
            )
        }
    }

    private fun checkBox(): MutableList<String> {
        val entrees: MutableList<String> = mutableListOf()
        if (isAdded && activity != null) {
            if (binding.ChckPagadas.isChecked) {
                entrees.add(binding.ChckPagadas.text.toString())
            }
            if (binding.ChckAnuladas.isChecked) {
                entrees.add(binding.ChckAnuladas.text.toString())
            }
            if (binding.ChckCuotaFija.isChecked) {
                entrees.add(binding.ChckCuotaFija.text.toString())
            }
            if (binding.ChckPendientesDePago.isChecked) {
                entrees.add(binding.ChckPendientesDePago.text.toString())
            }
            if (binding.ChckPlanDePago.isChecked) {
                entrees.add(binding.ChckPlanDePago.text.toString())
            }
        }
        return entrees
    }

    private fun listadoFiltrado(): MutableList<String> {
        val entrees: MutableList<String> = mutableListOf()
        if (isAdded && activity != null) {
            if (checkBox().isNotEmpty()) {
                entrees.add(getString(R.string.Filtrado_checkbox))
            }
            if (binding.btnCalendarDesde.text != getString(R.string.Filtrado_dia_mes_anio) && binding.btnCalendarHasta.text != getString(
                    R.string.Filtrado_dia_mes_anio
                )
            ) {
                entrees.add(getString(R.string.Filtrado_fechas))
            }
        }
        return entrees
    }

    private fun checkDates(): Boolean {
        if (
            (binding.btnCalendarDesde.text != getString(R.string.Filtrado_dia_mes_anio) && binding.btnCalendarHasta.text == getString(
                R.string.Filtrado_dia_mes_anio
            ))
            || (binding.btnCalendarDesde.text == getString(R.string.Filtrado_dia_mes_anio) && binding.btnCalendarHasta.text != getString(
                R.string.Filtrado_dia_mes_anio
            ))
        ) {
            alert.showAlert(
                getString(R.string.error),
                "Tienes que rellenar los dos campos",
                requireContext()
            )
            return false
        } else {
            return true
        }
    }

    private fun saveFilterStats() {
        val prefs = requireActivity().getSharedPreferences(
            getString(R.string.sheredPrefStats),
            Context.MODE_PRIVATE
        ).edit()
        prefs.clear()
        prefs.putString("Fecha_desde", binding.btnCalendarDesde.text.toString())
        prefs.putString("Fecha_hasta", binding.btnCalendarHasta.text.toString())
        prefs.putFloat("Precio", precio)
        prefs.putBoolean("Check_Pagadas", binding.ChckPagadas.isChecked)
        prefs.putBoolean("Check_Anuladas", binding.ChckAnuladas.isChecked)
        prefs.putBoolean("Check_PlanDePago", binding.ChckPlanDePago.isChecked)
        prefs.putBoolean("Check_Pendiente", binding.ChckPendientesDePago.isChecked)
        prefs.putBoolean("Check_CuotaFija", binding.ChckCuotaFija.isChecked)
        prefs.apply()
    }

    private fun checkStats() {
        val prefs = requireActivity().getSharedPreferences(getString(R.string.sheredPrefStats), Context.MODE_PRIVATE)
        binding.btnCalendarDesde.text = prefs.getString("Fecha_desde", getString(R.string.Filtrado_dia_mes_anio))
        binding.btnCalendarHasta.text = prefs.getString("Fecha_hasta", getString(R.string.Filtrado_dia_mes_anio))
        binding.volumeRange.setValues(prefs.getFloat("Precio", 0.0F))
        binding.ChckPagadas.isChecked = prefs.getBoolean("Check_Pagadas", false)
        binding.ChckAnuladas.isChecked = prefs.getBoolean("Check_Anuladas", false)
        binding.ChckPendientesDePago.isChecked = prefs.getBoolean("Check_Pendiente", false)
        binding.ChckPlanDePago.isChecked = prefs.getBoolean("Check_PlanDePago", false)
        binding.ChckCuotaFija.isChecked = prefs.getBoolean("Check_CuotaFija", false)
    }
}
