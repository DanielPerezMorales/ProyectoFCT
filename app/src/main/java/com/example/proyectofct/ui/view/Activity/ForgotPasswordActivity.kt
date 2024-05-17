package com.example.proyectofct.ui.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivityForgotPasswordBinding
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var fgViewModel:ForgotPasswordViewModel
    private val firebaseAuth =FirebaseAuth.getInstance()
    private val alert= Alert()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.forgot_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fgViewModel = ForgotPasswordViewModel(firebaseAuth)
        sendEmail()
        comeBackToLogin()
    }

    private fun sendEmail() {
        binding.btnEnviar.setOnClickListener {
            val email = binding.etEmail.text.toString()

            fgViewModel.sendEmail(email)
        }

        fgViewModel.forgotPasswordResult.observe(this, Observer { result ->
            val (success, errorMessage) = result
            if (success) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                alert.showAlert(
                    "Error",
                    errorMessage ?: "Error desconocido al enviar el email.",
                    this
                )
            }
        })
    }

    private fun comeBackToLogin(){
        binding.btnInicio.setOnClickListener{
            onBackPressed()
        }
    }
}
