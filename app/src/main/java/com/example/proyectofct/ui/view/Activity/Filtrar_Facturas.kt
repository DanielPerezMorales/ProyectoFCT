package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.DatePickerFragment
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.databinding.ActivityFiltrarFacturasBinding
import com.example.proyectofct.di.RoomModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class Filtrar_Facturas : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrarFacturasBinding
    private var precio: Float = 0.0f
    private val facturaModule = RoomModule
    private val alert = Alert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFiltrarFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.filtrado_facturas_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.ibCloseWindow.setOnClickListener {
            onBackPressed()
        }

        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val cambioColor = Firebase.remoteConfig.getBoolean("CambioDeValores")
                if (cambioColor) {
                    val colorConsumo = ContextCompat.getColor(this, R.color.color_consumo_2_0)
                    binding.ibCloseWindow.setColorFilter(colorConsumo)
                    binding.TVconFecha.setTypeface(null, Typeface.ITALIC)
                    binding.TVFacturas.setTypeface(null, Typeface.ITALIC)
                }
            }
        }

        binding.volumeRange.addOnChangeListener { _, value, _ ->
            saveVolume(value)
        }

        binding.btnAplicar.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val listaFiltrada: List<FacturaEntity> = apply(precio)
                if (listaFiltrada == null) {
                    alert.showAlert(
                        "ERROR",
                        "No se ha encontrado ninguna factura con estas características",
                        this@Filtrar_Facturas
                    )
                } else {
                    val intent = Intent(this@Filtrar_Facturas, Facturas::class.java)
                    intent.putExtra("ListaFiltrada", ArrayList(listaFiltrada))
                    startActivity(intent)
                }
            }
        }
        selectDate()
        delete()
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
            fecha_Desde!!.show(supportFragmentManager, "DATE_PICKER")
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
            fecha_Hasta!!.show(supportFragmentManager, "DATE_PICKER")
        }
    }

    private fun saveVolume(value: Float) {
        precio = value
    }

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
            if (month < 10) {
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
        binding.btnEliminarFiltro.setOnClickListener {
            binding.btnCalendarDesde.setText(R.string.dia_mes_anio)
            binding.btnCalendarHasta.setText(R.string.dia_mes_anio)
            binding.volumeRange.setValues(0.0F)
            binding.ChckPagadas.isChecked = false
            binding.ChckPendientesDePago.isChecked = false
            binding.ChckAnuladas.isChecked = false
            binding.ChckCuotaFija.isChecked = false
            binding.ChckPlanDePago.isChecked = false
        }
    }

    private suspend fun apply(value: Float): List<FacturaEntity> {
        val lista = facturaModule.provideRoom(this).getFactureDao().getAllFacturas()
        val listaReturn: MutableList<FacturaEntity> = mutableListOf()
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy")
        val fechaInicioText = binding.btnCalendarDesde.text.toString()
        val fechaFinText = binding.btnCalendarHasta.text.toString()
        val listaCheck: MutableList<String> = checkBox()

        val fechaInicio = if (fechaInicioText != getString(R.string.dia_mes_anio)) formatoFecha.parse(fechaInicioText) else null
        val fechaFin = if (fechaFinText != getString(R.string.dia_mes_anio)) formatoFecha.parse(fechaFinText) else null

        for (i in lista) {
            
            val fechaDentroRango = (fechaInicio == null || fechaInicio <= i.fecha) && (fechaFin == null || i.fecha <= fechaFin)

            if (fechaDentroRango && value <= i.precio && (listaCheck.isEmpty() || listaCheck.contains(i.estado))) {
                listaReturn.add(i)
            }
        }

        return listaReturn.toList()
    }


    private fun checkBox(): MutableList<String> {
        val entrees: MutableList<String> = mutableListOf()
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
        Log.i("TAG", "$entrees")
        return entrees
    }
}
