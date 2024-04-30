package com.example.proyectofct.domain

import com.google.firebase.auth.FirebaseAuth

class LoginUseCase(private val firebaseAuth: FirebaseAuth) {

    fun login(email: String, password: String, callback: (Boolean,String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            callback(false, "Por favor, ingresa tu correo electrónico y contraseña.")
            return
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val errorMessage = "Error desconocido al iniciar sesión."
                        callback(false, errorMessage)
                    }
                }
        }
    }
}
