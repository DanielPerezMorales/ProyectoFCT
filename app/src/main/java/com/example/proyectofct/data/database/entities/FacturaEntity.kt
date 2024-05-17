package com.example.proyectofct.data.database.entities

import android.annotation.SuppressLint
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectofct.data.model.FacturaItem
import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date

@Entity(tableName = "factura_tabla")
data class FacturaEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "fecha") var fecha: Date,
    @ColumnInfo(name = "estado") val estado: String,
    @ColumnInfo(name = "precio") val precio: Float
) : Serializable

@SuppressLint("SimpleDateFormat")
fun FacturaEntity.toFacturaItem(): FacturaItem {
    val formato = SimpleDateFormat("dd/MM/yyyy")
    val fechaFormateada = formato.format(fecha)
    return FacturaItem(fecha = fechaFormateada, descEstado = estado, importeOrdenacion = precio)
}