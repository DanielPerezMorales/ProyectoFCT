package com.example.proyectofct.ui.view.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.ViewPagerAdapter
import com.example.proyectofct.databinding.ActivityPantallaPrincipalSmartSolarBinding
import com.example.proyectofct.ui.view.Fragment.MiInstalacionFragment
import com.example.proyectofct.ui.view.Fragment.Detallesfragment
import com.example.proyectofct.ui.view.Fragment.EnergiaFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PantallaPrincipalSmartSolar : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaPrincipalSmartSolarBinding
    @Inject
    lateinit var detallesfragment: Detallesfragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityPantallaPrincipalSmartSolarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.smartsolar_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        createTab()

        binding.ibBack.setOnClickListener { onBackPressed() }
    }

    private fun createTab() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragmentWithTab(MiInstalacionFragment(), "Mi instalación")
        adapter.addFragmentWithTab(EnergiaFragment(),"Energía")
        adapter.addFragmentWithTab(detallesfragment, "Detalles")

        binding.VP.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.VP)
    }
}