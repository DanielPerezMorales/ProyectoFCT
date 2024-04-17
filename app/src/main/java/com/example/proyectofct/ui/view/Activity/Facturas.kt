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
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.database.entities.FacturaEntity
import com.example.proyectofct.data.database.entities.toFacturaItem
import com.example.proyectofct.data.model.FacturaAdapter_RV
import com.example.proyectofct.data.model.facturaItem
import com.example.proyectofct.data.model.toFacturaEntity
import com.example.proyectofct.data.network.FacturaService
import com.example.proyectofct.databinding.ActivityFacturasBinding
import com.example.proyectofct.di.RoomModule
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class Facturas : AppCompatActivity() {
    private lateinit var binding: ActivityFacturasBinding
    private lateinit var adapter: FacturaAdapter_RV
    private val facturaService = FacturaService()
    private val alert = Alert()
    private val facturaModule = RoomModule
    private var bundle: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.facturas_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        bundle = intent.extras
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val visualizarListado =
                    Firebase.remoteConfig.getBoolean("Visualizacion_ListadoFacturas")
                if (visualizarListado) {
                    if (bundle == null) {
                        initUI(true)
                    } else {
                        initUI(false)
                    }
                } else {
                    alert.showAlert("ERROR", "AHORA MISMO NO SE PUEDE VER", this)
                }

                val cambioColor = Firebase.remoteConfig.getBoolean("CambioDeValores")
                if (cambioColor) {
                    val colorConsumo: Int = ContextCompat.getColor(this, R.color.color_consumo_2_0)
                    binding.ibBack.setColorFilter(colorConsumo)
                    binding.TVConsumo.setTextColor(colorConsumo)
                    binding.TVFacturas.setTypeface(null, Typeface.ITALIC)
                }
            }
        }
        //setup()
        binding.ibFilter.setOnClickListener {
            val intent = Intent(this, Filtrar_Facturas::class.java)
            startActivity(intent)
        }

        binding.ibBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initUI(bool: Boolean) {
        adapter = FacturaAdapter_RV { showInformation() }
        binding.RVFacturas.layoutManager = LinearLayoutManager(this)
        binding.RVFacturas.adapter = adapter
        binding.RVFacturas.setHasFixedSize(true)
        putFacturasOnRecycler(bool)
    }

    private fun putFacturasOnRecycler(room: Boolean) {
        binding.PB.isVisible = true
        if (room) {
            CoroutineScope(Dispatchers.IO).launch {
                val response = facturaService.getFacturas()
                runOnUiThread {
                    if (response != null) {
                        //Log.i("PRUEBA", "FUNCIONAAAA")
                        adapter.updateList(response.facturas)
                        binding.PB.isVisible = false
                    }
                }
                if (response != null) {
                    deleteRoom()
                    insertAllOnRoom(response.facturas)
                }
            }
        } else {
            val fechaInicio = bundle?.getString("fechaInicio")
            val fechaFinal = bundle?.getString("fechaFinal")
            val precio = bundle?.getFloat("precio")
            val check_Pendiente = bundle?.getString("check_Pendiente")
            var lista: List<FacturaEntity> = listOf()
            CoroutineScope(Dispatchers.IO).launch {
                if (fechaInicio != null && fechaFinal != null && check_Pendiente != null && precio != null) {
                    lista = filtrePrecio(precio)
                }
                runOnUiThread {
                    adapter.updateList(lista.map { it.toFacturaItem() })
                    binding.PB.isVisible = false
                }
            }
        }
    }

    private suspend fun insertAllOnRoom(lista: List<facturaItem>) {
        val lista_entity: List<FacturaEntity> = lista.map { it.toFacturaEntity() }
        facturaModule.provideRoom(this).getFactureDao().insertAll(lista_entity)
    }

    private suspend fun deleteRoom() {
        facturaModule.provideRoom(this).getFactureDao().deleteAllFacturas()
    }

    private fun showInformation() {
        alert.showAlertInformation("Información", "Esta funcionalidad aún no está disponible", this)
    }

    private suspend fun showListFiltred(fechaInicio: String, fechaFinal: String, check_Pendiente: String, precio: Float): List<FacturaEntity> {
        return facturaModule.provideRoom(this).getFactureDao().getFacturasFiltradas(fechaInicio, fechaFinal, check_Pendiente, precio)
    }

    private suspend fun djwd(estado:String): List<FacturaEntity> {
        return facturaModule.provideRoom(this).getFactureDao().getFacturasFiltradasPorEstado(estado)
    }

    private suspend fun filtredFecha(fechaInicial:Date,fechaFinal: Date):List<FacturaEntity>{
        return facturaModule.provideRoom(this).getFactureDao().getFacturasFiltradasPorFecha(fechaInicial,fechaFinal)
    }

    private suspend fun filtrePrecio(precio:Float):List<FacturaEntity>{
        return facturaModule.provideRoom(this).getFactureDao().getFacturasFiltradasPorPrecio(precio)
    }

    /*private fun setup(){
        val bundle = intent.extras
        val dinero = bundle?.getInt("dinero")
        val isPaid = bundle?.getBoolean("check_pagadas")
        val isPendiente=bundle?.getBoolean("check_pendientes")
        Log.i("DANI","$dinero ,,,,,,, $isPaid,,,,,,,,,,,,,$isPendiente")
    }*/
}