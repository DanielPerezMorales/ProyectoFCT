package com.example.proyectofct.data.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.TypeConverter
import com.example.proyectofct.R
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class Converter @Inject constructor(private val contexto:Context){
    @SuppressLint("SimpleDateFormat")
    private val formatoFecha = SimpleDateFormat(contexto.getString(R.string.pattern_date))

    @TypeConverter
    fun desdeString(fechaString: String?): Date? {
        return fechaString?.let { formatoFecha.parse(it) }
    }

    @TypeConverter
    fun haciaString(fecha: Date?): String? {
        return fecha?.let { formatoFecha.format(it) }
    }
}
