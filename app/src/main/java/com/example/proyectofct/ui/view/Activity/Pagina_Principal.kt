package com.example.proyectofct.ui.view.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
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
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

class Pagina_Principal : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    private lateinit var secretKey: SecretKey
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.ibPractica1.setOnClickListener{
            val intent=Intent(this, Facturas::class.java)
            intent.putExtra("Mock",binding.SWMock.isChecked)
            startActivity(intent)
        }

        binding.ibPractica2.setOnClickListener{
            val intent=Intent(this, PantallaPrincipalSmartSolar::class.java)
            startActivity(intent)
        }

        binding.ibNavegacion.setOnClickListener{
            val intent=Intent(this, Activity_Navegacion::class.java)
            startActivity(intent)
        }

        binding.SWMock.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                Toast.makeText(this, "Mock activado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Mock desactivado", Toast.LENGTH_SHORT).show()
            }
        }

        binding.ibLogOut.setOnClickListener{
            val prefs =
                getSharedPreferences(getString(R.string.sheredPref), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()
            FirebaseAuth.getInstance().signOut()
            val intent=Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val configSettings:FirebaseRemoteConfigSettings= remoteConfigSettings {
            minimumFetchIntervalInSeconds=0
        }
        val firebaseConfig= Firebase.remoteConfig
        firebaseConfig.setConfigSettingsAsync(configSettings)
        firebaseConfig.setDefaultsAsync(mapOf(("Visualizacion_ListadoFacturas") to true, ("CambioDeValores") to false))

        // Genera o carga la clave de cifrado
        secretKey = generateOrLoadSecretKey()

        val bundle = intent.extras
        val email = bundle?.getString("email")
        val password = bundle?.getString("password")
        val check = bundle?.getBoolean("check")

        // Cifra y guarda los datos en SharedPreferences
        val encryptedEmail = encryptData(email ?: "")
        val encryptedPassword = encryptData(password ?: "")

        if(check!!){
            val prefs = getSharedPreferences(getString(R.string.sheredPref), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.putString("email", encryptedEmail)
            prefs.putString("password", encryptedPassword)
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