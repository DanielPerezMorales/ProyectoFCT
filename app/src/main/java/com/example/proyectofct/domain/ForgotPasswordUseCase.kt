package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.R
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class ForgotPasswordUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val contexto: Context
) {
    fun sendEmail(email: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank()) {
            callback(false, contexto.getString(R.string.UseCase_por_favor_ingresa_tu_correo_electr_nico))
            return
        } else {
            firebaseAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val errorMessage = contexto.getString(R.string.FG_Error)
                        callback(false, errorMessage)
                    }
                }
        }
    }
}
