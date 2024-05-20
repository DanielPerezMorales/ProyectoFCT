package com.example.proyectofct.ui.view.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.ViewPagerAdapter
import com.example.proyectofct.data.retrofit.model.FacturaAdapterRV
import com.example.proyectofct.databinding.ActivityFacturasBinding
import com.example.proyectofct.di.RoomModule
import com.example.proyectofct.ui.view.Fragment.FiltrarFacturasFragment
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

class Facturas : AppCompatActivity() {
    private lateinit var binding: ActivityFacturasBinding
    private lateinit var adapter: FacturaAdapterRV
    private val alert = Alert()
    private val facturaModule = RoomModule
    private val facturaViewModel: FacturasViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFacturasBinding.inflate(layoutInflater)
        val bundle: Bundle? =intent.extras
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.facturas_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val visualizarListado =
                    Firebase.remoteConfig.getBoolean("Visualizacion_ListadoFacturas")
                if (visualizarListado) {
                    if(bundle?.getBoolean("Mock") == true){
                        initUI(true)
                    } else {
                        initUI(false)
                    }
                } else {
                    alert.showAlert("ERROR", "AHORA MISMO NO SE PUEDE VER", this)
                }
            }
        }

        binding.ibFilter.setOnClickListener {
            binding.VP.visibility=View.VISIBLE
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(FiltrarFacturasFragment())
            binding.VP.adapter=adapter
        }


        binding.ibBack.setOnClickListener {
            onBackPressed()
        }

        showListFilter()
    }

    private fun mock() {
        binding.PB.isVisible = true
        facturaViewModel.putRetroMock(this, facturaModule.provideRoom(this))
        facturaViewModel.facturas.observe(this) { facturas ->
            facturas?.let {
                adapter.updateList(it)
                binding.PB.isVisible = false
            }
        }
    }

    private fun initUI(mock:Boolean) {
        adapter = FacturaAdapterRV { showInformation() }
        binding.RVFacturas.layoutManager = LinearLayoutManager(this)
        binding.RVFacturas.adapter = adapter
        binding.RVFacturas.setHasFixedSize(true)
        if(mock){
            mock()
        } else {
            putFacturasOnRecycler()
        }
    }

    private fun putFacturasOnRecycler() {
        binding.PB.isVisible = true
        facturaViewModel.fecthFacturasKTOR()
        facturaViewModel.facturas.observe(this) { facturas ->
            facturas?.let {
                adapter.updateList(it)
                binding.PB.isVisible = false
            }
        }
    }

    private fun showInformation() {
        alert.showAlertInformation("Información", "Esta funcionalidad aún no está disponible", this)
    }

    private fun showListFilter(){
        facturaViewModel.facturas.observe(this) { facturasFiltradas ->
            facturasFiltradas?.let {
                adapter.updateList(it)
            }
        }
    }
}