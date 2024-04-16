package com.example.proyectofct.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity (tableName = "factura_tabla")
data class FacturaEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id") val id:Int=0,
    @ColumnInfo(name = "fecha") val fecha: String,
    @ColumnInfo(name = "estado") val estado: String,
    @ColumnInfo(name = "precio") val precio: Float
)