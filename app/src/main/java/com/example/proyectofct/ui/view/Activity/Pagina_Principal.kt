package com.example.proyectofct.ui.view.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
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
import com.google.firebase.remoteconfig.remoteConfigSettings
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Pagina_Principal : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var secretKey: SecretKey
    private var int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        val configSettings: FirebaseRemoteConfigSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 0
        }
        val firebaseConfig = Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(
            mapOf("Visualizacion_ListadoFacturas" to true, "CambioDeValores" to false)
        )
        firebaseConfig.fetchAndActivate().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val cambioDeValores = firebaseConfig.getBoolean("CambioDeValores")
                if (cambioDeValores) {
                    Log.i("REMOTE_CONFIG", "Cambio de valores detectado")
                    int = R.style.Theme_ProyectoFCT_2_0
                    Log.i("THEME","${this.theme}")
                } else {
                    Log.i("REMOTE_CONFIG", "Cambio de valores no detectado")
                    int =R.style.Theme_ProyectoFCT
                    Log.i("THEME","${this.theme}")
                }
            } else {
                Log.e("Pagina_Principal", "Error fetching remote config", task.exception)
            }
        }
        setTheme(int)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibPractica1.setOnClickListener {
            val intent = Intent(this, Facturas::class.java)
            intent.putExtra("Mock", binding.SWMock.isChecked)
            startActivity(intent)
        }

        binding.ibPractica2.setOnClickListener {
            val intent = Intent(this, PantallaPrincipalSmartSolar::class.java)
            startActivity(intent)
        }

        binding.ibNavegacion.setOnClickListener {
            val intent = Intent(this, Activity_Navegacion::class.java)
            startActivity(intent)
        }

        binding.SWMock.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Mock activado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Mock desactivado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibLogOut.setOnClickListener {
            val prefs =
                getSharedPreferences(getString(R.string.sheredPref), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        // Genera o carga la clave de cifrado
        secretKey = generateOrLoadSecretKey()

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val password = bundle?.getString("password")
        val check = bundle?.getBoolean("check")
        val date = bundle?.getString("date")

        // Cifra y guarda los datos en SharedPreferences
        val encryptedEmail = encryptData(email ?: "")
        val encryptedPassword = encryptData(password ?: "")

        if (check == true) {
            val prefs = getSharedPreferences(
                getString(R.string.sheredPref),
                Context.MODE_PRIVATE
            ).edit()
            prefs.clear()
            prefs.putString("email", encryptedEmail)
            prefs.putString("password", encryptedPassword)
            prefs.putString("date", date)
            prefs.apply()
        }
    }

    private fun generateOrLoadSecretKey(): SecretKey {
        val keyGenerator = KeyGenerator.getInstance("AES")
        keyGenerator.init(256) // Selecciona la longitud de la clave (128, 192 o 256 bits)
        return keyGenerator.generateKey()
    }

    private fun encryptData(data: String): String {
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)
        val encryptedBytes = cipher.doFinal(data.toByteArray())
        return android.util.Base64.encodeToString(encryptedBytes, android.util.Base64.DEFAULT)
    }

}