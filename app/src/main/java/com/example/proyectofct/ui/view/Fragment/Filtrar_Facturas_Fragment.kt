package com.example.proyectofct.ui.view.Fragment

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager.widget.ViewPager
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.DatePickerFragment
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.databinding.FragmentFiltrarFacturasBinding
import com.example.proyectofct.di.RoomModule
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.concurrent.Semaphore

class Filtrar_Facturas_Fragment : Fragment() {
    private var precio: Float = 0.0f
    private val facturaModule = RoomModule
    private val facturaViewModel: FacturasViewModel by activityViewModels()
    private lateinit var binding: FragmentFiltrarFacturasBinding
    private var filtradoRealizado = false
    private val alert = Alert()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFiltrarFacturasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ibCloseWindow.setOnClickListener {
            delete()
            CoroutineScope(Dispatchers.IO).launch {
                apply(precio)
            }
            requireActivity().supportFragmentManager.beginTransaction().remove(this).commit()
            val vp = requireActivity().findViewById<ViewPager>(R.id.VP)
            vp.visibility = View.GONE
        }

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val cambioColor = Firebase.remoteConfig.getBoolean("CambioDeValores")
                if (cambioColor) {
                    val colorConsumo =
                        ContextCompat.getColor(requireContext(), R.color.color_consumo_2_0)
                    binding.ibCloseWindow.setColorFilter(colorConsumo)
                    binding.TVconFecha.setTypeface(null, Typeface.ITALIC)
                    binding.TVFacturas.setTypeface(null, Typeface.ITALIC)
                }
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            putMax()
        }

        binding.volumeRange.addOnChangeListener { _, value, _ ->
            saveVolume(value)
            putMaxSeleccionado(value)
        }

        selectDate()
        binding.btnEliminarFiltro.setOnClickListener {
            delete()
        }
        binding.btnAplicar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                apply(value = precio)
            }
        }
        comprobar()
    }


    private suspend fun putMax() {
        val Max: Float? = putMaxValue(
            facturaModule.provideRoom(requireContext()).getFactureDao().getAllFacturas()
        )
        if (Max != null) {
            binding.TVMaxPrecio.text = Max.toString()
        } else {
            binding.TVMaxPrecio.text = "100"
        }

        if (Max != null) {
            binding.volumeRange.valueTo = (Max.toInt()).toFloat()
            binding.volumeRange.stepSize = 1F
        }
    }

    private fun putMaxSeleccionado(value: Float) {
        binding.TVMaxPrecioSeleccionado.setText("    $value€")
    }

    private fun comprobar() {
        facturaViewModel.filtradoExitoso.observe(viewLifecycleOwner) { exitoso ->
            if (exitoso) {
                requireActivity().supportFragmentManager.beginTransaction()
                    .remove(this).commit()
                val vp = requireActivity().findViewById<ViewPager>(R.id.VP)
                vp.visibility = View.GONE
            } else {
                delete()
            }
        }
        filtradoRealizado = false
    }


    fun putMaxValue(lista: List<FacturaEntity>): Float? {
        var Max: Float? = null
        for (i in lista) {
            if (Max != null) {
                if (Max <= i.precio) {
                    Max = i.precio
                }
            } else {
                Max = i.precio
            }
        }
        return Max
    }

    private fun selectDate() {
        var fecha_Desde: DatePickerFragment? = null
        var fecha_Hasta: DatePickerFragment? = null

        binding.btnCalendarDesde.setOnClickListener {
            fecha_Desde = DatePickerFragment { year, month, day ->
                mostrarResultado(
                    year,
                    month,
                    day,
                    "Desde"
                )
            }
            fecha_Desde!!.show(childFragmentManager, "DATE_PICKER")
        }

        binding.btnCalendarHasta.setOnClickListener {
            fecha_Hasta = DatePickerFragment { year, month, day ->
                mostrarResultado(
                    year,
                    month,
                    day,
                    "Hasta"
                )
            }
            fecha_Hasta!!.show(childFragmentManager, "DATE_PICKER")
        }
    }

    private fun saveVolume(value: Float) {
        precio = value
    }

    @SuppressLint("SetTextI18n")
    private fun mostrarResultado(year: Int, month: Int, day: Int, boton: String) {
        if (boton.equals("Desde")) {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarDesde.setText("0$day/0${month + 1}/$year")
                } else {
                    binding.btnCalendarDesde.setText("$day/0${month + 1}/$year")
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarDesde.setText("0$day/${month + 1}/$year")
                } else {
                    binding.btnCalendarDesde.setText("$day/${month + 1}/$year")
                }
            }
        } else {
            if (month + 1 < 10) {
                if (day < 10) {
                    binding.btnCalendarHasta.setText("0$day/0${month + 1}/$year")
                } else {
                    binding.btnCalendarHasta.setText("$day/0${month + 1}/$year")
                }
            } else {
                if (day < 10) {
                    binding.btnCalendarHasta.setText("0$day/${month + 1}/$year")
                } else {
                    binding.btnCalendarHasta.setText("$day/${month + 1}/$year")
                }
            }
        }
    }

    private fun delete() {
        binding.btnCalendarDesde.setText(R.string.dia_mes_anio)
        binding.btnCalendarHasta.setText(R.string.dia_mes_anio)
        binding.volumeRange.setValues(0.0F)
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
                facturaModule.provideRoom(requireContext()).getFactureDao().getAllFacturas()
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
                requireContext(),
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
