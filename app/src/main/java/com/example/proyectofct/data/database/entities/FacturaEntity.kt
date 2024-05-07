package com.example.proyectofct.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.proyectofct.data.model.facturaItem
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

fun FacturaEntity.toFacturaItem(): facturaItem {
    val formato = SimpleDateFormat("dd/MM/yyyy")
    val fecha_formateada=formato.format(fecha)
    val entity = facturaItem(fecha = fecha_formateada, descEstado = estado, importeOrdenacion = precio)
    return entity
}