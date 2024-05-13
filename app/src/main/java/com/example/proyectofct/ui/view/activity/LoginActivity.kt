package com.example.proyectofct.ui.view.activity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.InputType.TYPE_CLASS_TEXT
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivityLoginBinding
import com.example.proyectofct.ui.viewmodel.LoginViewModel
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel
    private val firebaseAuth = FirebaseAuth.getInstance()
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
        viewModel = LoginViewModel(firebaseAuth = firebaseAuth)
        changeToCreateUser()
        forgotPassword()
        binding.btnEntrar.setOnClickListener {
            login(binding.etUsuario.text.toString(), binding.etPassword.text.toString())
        }
        seePassword()
        logWithFingerPrint()
        binding.fingerprint.setOnClickListener {
            if (auth) {
                auth = false
            } else {
                authenticate {
                    auth = it
                }
            }
        }
        sessionActive()
    }

    private fun sessionActive() {
        binding.PB.visibility = View.VISIBLE
        val prefs = getSharedPreferences(getString(R.string.sheredPref), Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val password = prefs.getString("password", null)
        val date = prefs.getString("date", "1970-01-01")
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Calendar.getInstance()
        currentDate.set(Calendar.HOUR_OF_DAY, 0)
        currentDate.set(Calendar.MINUTE, 0)
        currentDate.set(Calendar.SECOND, 0)
        currentDate.set(Calendar.MILLISECOND, 0)
        if (formatter.parse(date) >= currentDate.time) {
            if (email != null && password != null) {
                val intent = Intent(this, Pagina_Principal::class.java)
                intent.putExtra("email", email)
                intent.putExtra("password", password)
                intent.putExtra("date", date)
                startActivity(intent)
            } else {
                binding.PB.visibility = View.GONE
            }
        } else {
            binding.PB.visibility = View.GONE
            Toast.makeText(this, "La sesión ha caducado", Toast.LENGTH_SHORT).show()
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
                        val intent = Intent(this@LoginActivity, Pagina_Principal::class.java)
                        intent.putExtra("email", binding.etUsuario.text)
                        intent.putExtra("password", binding.etPassword.text)
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

    @SuppressLint("ClickableViewAccessibility")
    private fun seePassword() {
        binding.ojoInformacion.setOnTouchListener { view, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.etPassword.inputType = TYPE_CLASS_TEXT
                    binding.etPassword.setSelection(binding.etPassword.text.length)
                    true
                }
                MotionEvent.ACTION_UP -> {
                    binding.etPassword.inputType = 129
                    binding.etPassword.setSelection(binding.etPassword.text.length)
                    true
                }
                else -> false
            }
        }
    }

    private fun login(email: String, password: String) {
        /*
        Para entrar :)
        emailprueba@gmail.com
        123456
        */
        viewModel.login(email, password)

        val check = binding.chckBX.isChecked

        viewModel.loginResult.observe(this, Observer { result ->
            val (success, errorMessage) = result
            if (success) {
                if (check) {
                    val intent = Intent(this, Pagina_Principal::class.java)
                    intent.putExtra("email", email)
                    intent.putExtra("password", password)
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    intent.putExtra("date", formatter.format(Calendar.getInstance().time))
                    intent.putExtra("check", check)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, Pagina_Principal::class.java)
                    intent.putExtra("check", check)
                    startActivity(intent)
                }

            } else {
                alert.showAlert(
                    "Error",
                    errorMessage ?: "Error desconocido al iniciar sesión.",
                    this
                )
            }
        })
    }

}
