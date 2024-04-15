package com.example.proyectofct.domain

import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordUseCase(private val firebaseAuth: FirebaseAuth) {
    fun sendEmail(email: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank()) {
            callback(false, "Por favor, ingresa tu correo electrónico")
            return
        }

        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    val errorMessage = "Error desconocido al enviar el email."
                    callback(false, errorMessage)
                }
            }
    }
}
