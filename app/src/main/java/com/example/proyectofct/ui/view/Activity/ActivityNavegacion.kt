package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityNavegacionBinding

class ActivityNavegacion : AppCompatActivity() {
    private lateinit var binding:ActivityNavegacionBinding
    private val linkIberdrolaWeb="https://www.iberdrola.es"
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

        binding.botonWeb.setOnClickListener {
            val link=Uri.parse(linkIberdrolaWeb)
            binding.WB.loadUrl("")
            val intent= Intent(Intent.ACTION_VIEW, link)
            startActivity(intent)
        }

        binding.botonWebView.setOnClickListener {
            binding.WB.loadUrl(linkIberdrolaWeb)
        }
    }
}