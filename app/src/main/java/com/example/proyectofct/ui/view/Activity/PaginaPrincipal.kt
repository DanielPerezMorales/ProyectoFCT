package com.example.proyectofct.ui.view.Activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

class PaginaPrincipal : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var secretKey: SecretKey
    private var themeApplied = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            themeApplied = savedInstanceState.getBoolean(getString(R.string.themeapplied), false)
            applyThemeIfNeeded()
        } else {
            setUpRemoteConfig()
        }
        enableEdgeToEdge()
        val bundle = intent.extras
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibPractica1.setOnClickListener {
            val intent = Intent(this, Facturas::class.java)
            intent.putExtra(getString(R.string.mock), binding.SWMock.isChecked)
            intent.putExtra(getString(R.string.ktor), binding.SWKTOR.isChecked)
            startActivity(intent)
        }

        binding.ibPractica2.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalSmartSolar::class.java)
            startActivity(intent)
        }

        binding.ibNavegacion.setOnClickListener {
            val intent = Intent(this, ActivityNavegacion::class.java)
            startActivity(intent)
        }

        binding.SWMock.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, getString(R.string.mock_activado), Toast.LENGTH_SHORT).show()
                binding.SWKTOR.isChecked = false
            } else {
                Toast.makeText(this, getString(R.string.mock_desactivado), Toast.LENGTH_SHORT).show()
            }
        }

        binding.SWKTOR.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                Toast.makeText(this, getString(R.string.ktor_activado), Toast.LENGTH_SHORT).show()
                binding.SWMock.isChecked = false
            } else {
                Toast.makeText(this, getString(R.string.ktor_desactivado), Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibLogOut.setOnClickListener {
            val prefs = getSharedPreferences(getString(R.string.sheredPref), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.putString(getString(R.string.date), bundle?.getString(getString(R.string.date)))
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        secretKey = generateOrLoadSecretKey()

        val email = bundle?.getString(getString(R.string.email))
        val password = bundle?.getString(getString(R.string.password))
        val check = bundle?.getBoolean(getString(R.string.check))
        val date = bundle?.getString(getString(R.string.date))

        // Cifra y guarda los datos en SharedPreferences
        val encryptedEmail = encryptData(email ?: getString(R.string.espacio_vacio))
        val encryptedPassword = encryptData(password ?: getString(R.string.espacio_vacio))

        if (check == true) {
            val prefs = getSharedPreferences(
                getString(R.string.sheredPref),
                Context.MODE_PRIVATE
            ).edit()
            prefs.clear()
            prefs.putString(getString(R.string.email), encryptedEmail)
            prefs.putString(getString(R.string.password), encryptedPassword)
            prefs.putString(getString(R.string.date), date)
            prefs.apply()
        }
    }

    private fun generateOrLoadSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance(getString(R.string.aes))
        keyGenerator.init(256)
        return keyGenerator.generateKey()
    }

    @SuppressLint("GetInstance")
    private fun encryptData(data: String): String {
        val cipher = Cipher.getInstance(getString(R.string.aes))
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(getString(R.string.themeapplied), themeApplied)
        super.onSaveInstanceState(outState)
    }

    private fun setUpRemoteConfig() {
        val configSettings: FirebaseRemoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        val firebaseConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(
            mapOf(getString(R.string.visualizacion_listadofacturas) to true,
                getString(R.string.cambiodevalores) to false)
        )
        firebaseConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val cambioDeValores = firebaseConfig.getBoolean(getString(R.string.cambiodevalores))
                if (cambioDeValores && !themeApplied) {
                    setTheme(R.style.Theme_ProyectoFCT_2_0)
                    themeApplied = true
                    recreate()
                }
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        themeApplied = savedInstanceState.getBoolean(getString(R.string.themeapplied), false)
        applyThemeIfNeeded()
    }

    private fun applyThemeIfNeeded() {
        if (themeApplied) {
            setTheme(R.style.Theme_ProyectoFCT_2_0)
        } else {
            setTheme(R.style.Theme_ProyectoFCT)
        }
    }

}