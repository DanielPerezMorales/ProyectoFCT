package com.example.proyectofct.domain

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SignUpUseCase @Inject constructor(private val firebaseAuth: FirebaseAuth) {
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            callback(false, "Por favor, ingresa tu correo electrónico y contraseña.")
            return
        } else {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val errorMessage = "Error desconocido al crear usuario."
                        callback(false, errorMessage)
                    }
                }
        }
    }
}