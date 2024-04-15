package com.example.proyectofct.core

import android.content.Context
import androidx.appcompat.app.AlertDialog
import okhttp3.internal.http2.Http2Connection.Listener

class Alert {
    fun showAlert(titulo: String,mensaje: String, cont: Context) {
        val builder = AlertDialog.Builder(cont)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    fun showAlertInformation(titulo: String,mensaje: String, cont: Context) {
        val builder = AlertDialog.Builder(cont)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)
        builder.setPositiveButton("Cerrar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}