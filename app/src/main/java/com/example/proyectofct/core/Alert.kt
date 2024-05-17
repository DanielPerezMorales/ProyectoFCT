package com.example.proyectofct.core

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.example.proyectofct.R
import com.example.proyectofct.ui.view.fragment.Detalles_fragment

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

    fun showAlertYesOrNo(titulo: String, mensaje: String, cont: Context, yesAction: () -> Unit) {
        val builder = AlertDialog.Builder(cont)
        builder.setTitle(titulo)
        builder.setMessage(mensaje)

        builder.setNegativeButton("Sí") { _, _ ->
            yesAction()
        }

        builder.setPositiveButton("No") { _, _ ->
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()

        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener {
            dialog.dismiss()
        }
    }

    fun showPopNative(cont: Detalles_fragment) {
        val inflater = LayoutInflater.from(cont.requireContext())
        val dialogView = inflater.inflate(R.layout.pop_nativo_informacion, null)
        val builder = AlertDialog.Builder(cont.requireContext())
        builder.setView(dialogView)
        val dialog = builder.create()
        val dismissButton = dialogView.findViewById<Button>(R.id.btn_Aceptar)
        dismissButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
    fun showPopNative_Context(cont: Context) {
        val inflater = LayoutInflater.from(cont)
        val dialogView = inflater.inflate(R.layout.pop_nativo_informacion, null)
        val builder = AlertDialog.Builder(cont)
        builder.setView(dialogView)
        val dialog = builder.create()
        val dismissButton = dialogView.findViewById<Button>(R.id.btn_Aceptar)
        dismissButton.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}