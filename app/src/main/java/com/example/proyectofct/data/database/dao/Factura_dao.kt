package com.example.proyectofct.data.database.dao

import android.util.Log
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofct.data.database.entities.FacturaEntity
import java.util.Date

@Dao
interface Factura_dao {
    @Query("SELECT * FROM factura_tabla")
    suspend fun getAllFacturas():List<FacturaEntity>

    @Query("SELECT * FROM factura_tabla WHERE strftime('%d-%m-%Y', fecha) BETWEEN :fechaDesde AND :fechaHasta AND estado = :estado AND precio >= :precio")
    suspend fun getFacturasFiltradas(fechaDesde: String, fechaHasta: String, estado: String, precio: Float):List<FacturaEntity>

    @Query("SELECT * FROM factura_tabla WHERE fecha BETWEEN :fechaDesde AND :fechaHasta")
    suspend fun getFacturasFiltradasPorFecha(fechaDesde: Date, fechaHasta: Date): List<FacturaEntity>


    @Query("SELECT * FROM factura_tabla WHERE estado = :estado")
    suspend fun getFacturasFiltradasPorEstado(estado: String):List<FacturaEntity>

    @Query("SELECT * FROM factura_tabla WHERE precio <= :estado")
    suspend fun getFacturasFiltradasPorPrecio(estado: Float):List<FacturaEntity>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facturas:List<FacturaEntity>)

    @Query("DELETE FROM factura_tabla")
    suspend fun deleteAllFacturas()
}