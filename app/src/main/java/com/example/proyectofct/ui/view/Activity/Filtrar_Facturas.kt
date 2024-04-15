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
import com.example.proyectofct.databinding.ActivityFiltrarFacturasBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Filtrar_Facturas : AppCompatActivity() {
    private lateinit var binding: ActivityFiltrarFacturasBinding
    private var alert=Alert()

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

    private fun mostrarResultado(year: Int, month: Int, day: Int, boton: String) {
        if (boton.equals("Desde")) {
            binding.btnCalendarDesde.setText("$year/$month/$day")
        } else {
            binding.btnCalendarHasta.setText("$year/$month/$day")
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

    private fun apply() {
        binding.btnAplicar.setOnClickListener {

        }
    }
}
