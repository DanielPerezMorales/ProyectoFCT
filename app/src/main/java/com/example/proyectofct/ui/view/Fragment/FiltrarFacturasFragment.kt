package com.example.proyectofct.ui.view.Fragment

import android.annotation.SuppressLint
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
    lateinit var alert : Alert

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
            delete()
            CoroutineScope(Dispatchers.IO).launch { apply(precio) }
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            val vp = requireActivity().findViewById<ViewPager>(R.id.VP)
            vp.visibility = View.GONE
        }

        CoroutineScope(Dispatchers.IO).launch { putValues() }

        binding.volumeRange.addOnChangeListener { _, value, _ ->
            putMaxSeleccionadoAndPrecio(value)
        }

        selectDate()
        binding.btnEliminarFiltro.setOnClickListener {
            alert.showAlertYesOrNo("Borrado",
                "¿Seguro que quieres borrar el filtrado?",
                requireContext()
            , yesAction = { delete() }, noAction = {})
        }
        binding.btnAplicar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch { apply(value = precio) }
        }
        comprobar()
    }


    @SuppressLint("SetTextI18n")
    private suspend fun putValues() {
        val max: Float? = putMaxValue(
            facturaDatabase.getFactureDao().getAllFacturas()
        )
        if (max != null) {
            binding.TVMaxPrecio.text = max.toString()
        } else {
            binding.TVMaxPrecio.text = "100"
            binding.TVMinPrecio.text = "0"
        }

        if (max != null) {
            binding.volumeRange.valueTo = (max.toInt() + 1).toFloat()
            binding.volumeRange.stepSize = 1F
        }
    }

    @SuppressLint("SetTextI18n")
    private fun putMaxSeleccionadoAndPrecio(value: Float) {
        binding.TVMaxPrecioSeleccionado.text = "    $value€"
        precio = value
    }

    private fun comprobar() {
        facturaViewModel.filtradoExitoso.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this).commit()
                val vp = requireActivity().findViewById<ViewPager>(R.id.VP)
                vp.visibility = View.GONE
                facturaViewModel.putAgainOnFalse()
                filtradoRealizado = false
            } else {
                if (filtradoRealizado) {
                    alert.showAlert(
                        "ERROR",
                        "No hay facturas que cumplan estos requisitos",
                        requireContext()
                    )
                }
                filtradoRealizado = false
                delete()
            }
        }
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
                mostrarResultado(year, month, day, "Desde")
            }
            fechaDesde!!.show(childFragmentManager, "DATE_PICKER")
        }

        binding.btnCalendarHasta.setOnClickListener {
            fechaHasta = DatePickerFragment { year, month, day ->
                mostrarResultado(year, month, day, "Hasta")
            }
            fechaHasta!!.show(childFragmentManager, "DATE_PICKER")
        }
    }


    @SuppressLint("SetTextI18n")
    private fun mostrarResultado(year: Int, month: Int, day: Int, boton: String) {
        if (boton == "Desde") {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarDesde.text = "0$day/0${month + 1}/$year"
                } else {
                    binding.btnCalendarDesde.text = "$day/0${month + 1}/$year"
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarDesde.text = "0$day/${month + 1}/$year"
                } else {
                    binding.btnCalendarDesde.text = "$day/${month + 1}/$year"
                }
            }
        } else {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarHasta.text = "0$day/0${month + 1}/$year"
                } else {
                    binding.btnCalendarHasta.text = "$day/0${month + 1}/$year"
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarHasta.text = "0$day/${month + 1}/$year"
                } else {
                    binding.btnCalendarHasta.text = "$day/${month + 1}/$year"
                }
            }
        }
    }

    private fun delete() {
        binding.btnCalendarDesde.setText(R.string.dia_mes_anio)
        binding.btnCalendarHasta.setText(R.string.dia_mes_anio)
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
            val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
            val fechaInicioText = binding.btnCalendarDesde.text.toString()
            val fechaFinText = binding.btnCalendarHasta.text.toString()
            val listaCheck: MutableList<String> = checkBox()
            val fechaInicio =
                if (fechaInicioText != "dia/mes/año") formatoFecha.parse(fechaInicioText) else null
            val fechaFin =
                if (fechaFinText != "dia/mes/año") formatoFecha.parse(fechaFinText) else null
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
                entrees.add("CheckBox")
            }
            if (binding.btnCalendarDesde.text != getString(R.string.dia_mes_anio) && binding.btnCalendarDesde.text != getString(
                    R.string.dia_mes_anio
                )
            ) {
                entrees.add("Fechas")
            }
        }
        return entrees
    }
}
