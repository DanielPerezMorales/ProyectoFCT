package com.example.proyectofct.ui.view.Activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.ViewPagerAdapter
import com.example.proyectofct.databinding.ActivityPantallaPrincipalSmartSolarBinding
import com.example.proyectofct.ui.view.fragment.Mi_instalacion_fragment
import com.example.proyectofct.ui.view.fragment.Detalles_fragment
import com.example.proyectofct.ui.view.fragment.Energia_fragment

class PantallaPrincipalSmartSolar : AppCompatActivity() {
    private lateinit var binding: ActivityPantallaPrincipalSmartSolarBinding
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
        adapter.addFragmentWithTab(Mi_instalacion_fragment(), "Mi instalación")
        adapter.addFragmentWithTab(Energia_fragment(),"Energía")
        adapter.addFragmentWithTab(Detalles_fragment(), "Detalles")

        binding.VP.adapter = adapter
        binding.tablayout.setupWithViewPager(binding.VP)
    }
}