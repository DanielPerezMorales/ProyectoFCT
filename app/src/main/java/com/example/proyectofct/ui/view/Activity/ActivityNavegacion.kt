package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.LINK
import com.example.proyectofct.databinding.ActivityNavegacionBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ActivityNavegacion: AppCompatActivity() {
    private lateinit var binding:ActivityNavegacionBinding
    @Inject
    @LINK lateinit var linkIberdrolaWeb:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityNavegacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.WB.settings.allowContentAccess

        binding.botonWeb.setOnClickListener {
            val link=Uri.parse(linkIberdrolaWeb)
            binding.WB.loadUrl(getString(R.string.espacio_vacio))
            val intent= Intent(Intent.ACTION_VIEW, link)
            startActivity(intent)
        }

        binding.botonWebView.setOnClickListener {
            binding.WB.clearCache(true)
            binding.WB.loadUrl(linkIberdrolaWeb)
        }

        binding.ibBack.setOnClickListener{ onBackPressedDispatcher.onBackPressed() }
    }
}