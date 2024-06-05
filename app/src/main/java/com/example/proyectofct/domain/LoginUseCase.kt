package com.example.proyectofct.domain

import android.content.Context
import com.example.proyectofct.R
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val contexto: Context
) {

    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            callback(false,
                contexto.getString(R.string.por_favor_ingresa_tu_correo_electr_nico_y_contrase_a))
            return
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback(true, null)
                    } else {
                        val errorMessage = contexto.getString(R.string.error_desconocido_al_iniciar_sesi_n)
                        callback(false, errorMessage)
                    }
                }
        }
    }
}
