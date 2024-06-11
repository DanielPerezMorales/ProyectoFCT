package com.example.proyectofct.ui.view.Activity

import android.content.Context
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
import com.example.proyectofct.ui.view.Fragment.FiltrarFacturasFragment
import com.example.proyectofct.ui.viewmodel.FacturasViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class Facturas : AppCompatActivity() {
    private lateinit var binding: ActivityFacturasBinding
    private lateinit var adapter: FacturaAdapterRV

    @Inject
    lateinit var alert: Alert
    private val facturaViewModel: FacturasViewModel by viewModels()
    private var ktor = false

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
        val bundle: Bundle? = intent.extras
        Firebase.remoteConfig.fetchAndActivate().addOnCompleteListener {
            if (it.isSuccessful) {
                val visualizarListado =
                    Firebase.remoteConfig.getBoolean(getString(R.string.visualizacion_listadofacturas))
                if (visualizarListado) {
                    if (bundle?.getBoolean(getString(R.string.mock)) == true) {
                        initUI(true)
                    } else {
                        if (bundle?.getBoolean(getString(R.string.ktor)) == true) {
                            ktor = true
                        }
                        initUI(false)
                    }
                } else {
                    alert.showAlertYesOrNo(
                        getString(R.string.error),
                        getString(R.string.label_remotrcongif_vistadoFacturaFalse),
                        this, { onBackPressedDispatcher.onBackPressed() }, noAction = {})
                }
            }
        }

        val prefs = getSharedPreferences(getString(R.string.sheredPrefStats), Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        binding.ibFilter.setOnClickListener {
            val adapter = ViewPagerAdapter(supportFragmentManager)
            adapter.addFragment(FiltrarFacturasFragment())
            binding.VP.adapter = adapter
            binding.VP.visibility = View.VISIBLE
        }


        binding.ibBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        showListFilter()
    }

    private fun mock() {
        binding.PB.isVisible = true
        facturaViewModel.putRetroMock()
        facturaViewModel.showEmptyDialog.observe(this) { it ->
            if (it) {
                alert.showAlertYesOrNo(
                    getString(R.string.error),
                    getString(R.string.label_nohayNadaParaMostrar),
                    this, { onBackPressedDispatcher.onBackPressed() }, noAction = {})
            } else {
                facturaViewModel.facturas.observe(this) { facturas ->
                    facturas?.let {
                        adapter.updateList(it)
                        binding.PB.isVisible = false
                    }
                }
            }
        }
    }

    private fun initUI(mock: Boolean) {
        adapter = FacturaAdapterRV { showInformation() }
        binding.RVFacturas.layoutManager = LinearLayoutManager(this)
        binding.RVFacturas.adapter = adapter
        binding.RVFacturas.setHasFixedSize(true)
        if (mock) {
            mock()
        } else {
            putFacturasOnRecycler()
        }
    }

    private fun putFacturasOnRecycler() {
        binding.PB.isVisible = true
        if (ktor) {
            facturaViewModel.fecthFacturasKTOR()
        } else {
            facturaViewModel.fetchFacturas()
        }
        facturaViewModel.showEmptyDialog.observe(this) { it ->
            if (it) {
                alert.showAlertYesOrNo(
                    getString(R.string.error),
                    getString(R.string.label_nohayNadaParaMostrar),
                    this, { onBackPressedDispatcher.onBackPressed() }, noAction = {})
            } else {
                facturaViewModel.facturas.observe(this) { facturas ->
                    facturas?.let {
                        adapter.updateList(it)
                        binding.PB.isVisible = false
                    }
                }
            }
        }
    }

    private fun showInformation() {
        alert.showAlertInformation(
            getString(R.string.informacion),
            getString(R.string.infomracion_text),
            this
        )
    }

    private fun showListFilter() {
        facturaViewModel.facturas.observe(this) { facturasFiltradas ->
            facturasFiltradas?.let {
                adapter.updateList(it)
            }
        }
    }
}