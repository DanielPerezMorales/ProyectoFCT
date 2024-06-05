package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivityForgotPasswordBinding
import com.example.proyectofct.ui.viewmodel.ForgotPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val fgViewModel: ForgotPasswordViewModel by viewModels()

    @Inject
    lateinit var alert: Alert
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
        sendEmail()
        comeBackToLogin()
    }

    private fun sendEmail() {
        binding.btnEnviar.setOnClickListener {
            val email = binding.etEmail.text.toString()

            fgViewModel.sendEmail(email)
        }

        fgViewModel.forgotPasswordResult.observe(this) { result ->
            val (success, errorMessage) = result
            if (success) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                alert.showAlert(
                    getString(R.string.error),
                    errorMessage ?: getString(R.string.fgError),
                    this
                )
            }
        }
    }

    private fun comeBackToLogin() {
        binding.btnInicio.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
    }
}
