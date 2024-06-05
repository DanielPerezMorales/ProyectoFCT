package com.example.proyectofct.data.database

import android.annotation.SuppressLint
import android.content.Context
import androidx.room.TypeConverter
import com.example.proyectofct.R
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class Converter {
    @SuppressLint("SimpleDateFormat")
    private val formatoFecha = SimpleDateFormat("yyyy-MM-dd")

    @TypeConverter
    fun desdeString(fechaString: String?): Date? {
        return fechaString?.let { formatoFecha.parse(it) }
    }

    @TypeConverter
    fun haciaString(fecha: Date?): String? {
        return fecha?.let { formatoFecha.format(it) }
    }
}
