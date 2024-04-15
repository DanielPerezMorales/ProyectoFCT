package com.example.proyectofct.core

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.Calendar

class DatePickerFragment (val listener:(year:Int, month:Int, day:Int)->Unit): DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c= Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireActivity(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, day: Int) {
        listener(year, month, day)
    }

    fun getYear(): Int? {
        var anio=0
        DatePickerFragment{year, month, day ->
            anio=year
        }
        return anio
    }

    fun getMonth(): Int? {
        var mes=0
        DatePickerFragment{year, month, day ->
            mes= month
        }
        return mes
    }

    fun getDay(): Int? {
        var dia=0
        DatePickerFragment{year, month, day ->
            dia= day
        }
        return dia
    }
}