package com.example.proyectofct.data.database

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date

class Converter {
    private val formatoFecha = SimpleDateFormat("dd/MM/yyyy")

    @TypeConverter
    fun desdeString(fechaString: String?): Date? {
        return fechaString?.let { formatoFecha.parse(it) }
    }

    @TypeConverter
    fun haciaString(fecha: Date?): String? {
        return fecha?.let { formatoFecha.format(it) }
    }
}
