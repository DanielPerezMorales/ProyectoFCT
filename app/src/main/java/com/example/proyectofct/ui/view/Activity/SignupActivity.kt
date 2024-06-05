package com.example.proyectofct.ui.view.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivitySignupBinding
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@AndroidEntryPoint
class SignupActivity : AppCompatActivity() {
    @Inject
    lateinit var alert: Alert
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        changeToLogin()
        createUser()
        seePassword()
    }

    @SuppressLint("SimpleDateFormat")
    private fun createUser() {
        binding.btnCrearUsuario.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.signUp(email, password)
        }

        viewModel.signupResult.observe(this) { result ->
            val (success, errorMessage) = result
            if (success) {
                alert.showAlertYesOrNo("Usuario Creado", "¿Quieres dejar la sesión activa?", this, {
                    val intent = Intent(this, PaginaPrincipal::class.java)
                    intent.putExtra("email", binding.etEmail.text.toString())
                    intent.putExtra("password", binding.etPassword.text.toString())
                    val formatter = SimpleDateFormat("yyyy-MM-dd")
                    intent.putExtra("date", formatter.format(Calendar.getInstance().time))
                    intent.putExtra("check", true)
                    startActivity(intent)
                }, noAction = {
                    val intent = Intent(this, PaginaPrincipal::class.java)
                    startActivity(intent)
                })
            } else {
                alert.showAlert(
                    "Error",
                    errorMessage ?: "Error desconocido al crear usuario.",
                    this
                )
            }
        }
    }

    private fun changeToLogin() {
        binding.btnInicio.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun seePassword() {
        binding.ojoInformacion.setOnTouchListener { _, motionEvent ->
            when (motionEvent.action) {
                MotionEvent.ACTION_DOWN -> {
                    binding.etPassword.inputType = InputType.TYPE_CLASS_TEXT
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
}