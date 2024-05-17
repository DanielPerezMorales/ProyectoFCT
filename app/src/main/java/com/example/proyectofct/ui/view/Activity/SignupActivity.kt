package com.example.proyectofct.ui.view.Activity

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectofct.R
import com.example.proyectofct.core.Alert
import com.example.proyectofct.databinding.ActivitySignupBinding
import com.example.proyectofct.ui.viewmodel.SignUpViewModel
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private val alert= Alert()
    private lateinit var viewModel: SignUpViewModel
    private val firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.signup_layout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        viewModel = SignUpViewModel(firebaseAuth = firebaseAuth)

        changeToLogin()
        createUser()
        seePassword()
    }

    private fun createUser() {
        binding.btnCrearUsuario.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            viewModel.signUp(email, password)
        }

        viewModel.signupResult.observe(this) { result ->
            val (success, errorMessage) = result
            if (success) {
                val intent = Intent(this, PaginaPrincipal::class.java)
                startActivity(intent)
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
        binding.btnInicio.setOnClickListener{
            val intent= Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun seePassword(){
        binding.ojoInformacion.setOnClickListener {
            if(binding.etPassword.inputType!= InputType.TYPE_CLASS_TEXT){
                //Log.i("PRUEEEEEEEEEEBAAAAA", binding.etPassword.inputType.toString())
                binding.etPassword.inputType= InputType.TYPE_CLASS_TEXT
            } else{
                binding.etPassword.inputType=129
            }
        }
    }
}