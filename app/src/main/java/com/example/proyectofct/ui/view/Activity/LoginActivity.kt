package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.text.InputType
import android.text.InputType.*
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.CompoundButtonCompat
import androidx.lifecycle.Observer
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivityLoginBinding
import com.example.proyectofct.domain.LoginUseCase
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private val alert = Alert()
    private var auth = false
    private var canAuthenticate = false
    private lateinit var promptInfo: BiometricPrompt.PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.login_activity)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val color = ContextCompat.getColor(this, R.color.white)
        CompoundButtonCompat.setButtonTintList(binding.chckBX, ColorStateList.valueOf(color))
        changeToCreateUser()
        forgotPassword()
        login()
        seePassword()
        hack()
        logWithFingerPrint()
        binding.fingerprint.setOnClickListener{
            if(auth){
                auth=false
            } else {
                authenticate {
                    auth=it
                }
            }
        }
    }

    private fun logWithFingerPrint() {
        if (BiometricManager.from(this)
                .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL) == BiometricManager.BIOMETRIC_SUCCESS
        ) {
            canAuthenticate = true
            promptInfo = BiometricPrompt.PromptInfo.Builder().setTitle("Autenticación biométrica")
                .setSubtitle("Autenticate utilizando el sensor biométrico")
                .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build()
        }
    }

    private fun authenticate(auth: (auth: Boolean) -> Unit) {
        if (canAuthenticate) {
            BiometricPrompt(
                this,
                ContextCompat.getMainExecutor(this),
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        val intent=Intent(this@LoginActivity,Pagina_Principal::class.java)
                        startActivity(intent)

                    }
                }).authenticate(promptInfo)
        } else {
            auth(true)
        }
    }

    private fun forgotPassword() {
        binding.TVRecuperarDatos.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun changeToCreateUser() {
        binding.btnRegistrar.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun seePassword() {
        binding.ojoInformacion.setOnClickListener {
            if (binding.etPassword.inputType != TYPE_CLASS_TEXT) {
                //Log.i("PRUEEEEEEEEEEBAAAAA", binding.etPassword.inputType.toString())
                binding.etPassword.inputType = TYPE_CLASS_TEXT
            } else {
                binding.etPassword.inputType = 129
            }
        }
    }

    private fun login() {
        binding.btnEntrar.setOnClickListener {
            val email = binding.etUsuario.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.login(email, password)
        }

        viewModel.loginResult.observe(this, Observer { result ->
            val (success, errorMessage) = result
            if (success) {
                val intent = Intent(this, Pagina_Principal::class.java)
                startActivity(intent)
            } else {
                alert.showAlert(
                    "Error",
                    errorMessage ?: "Error desconocido al iniciar sesión.",
                    this
                )
            }
        })
    }


    private fun hack() {
        binding.hack.setOnClickListener {
            val intent = Intent(this, Pagina_Principal::class.java)
            startActivity(intent)
        }
    }

}
