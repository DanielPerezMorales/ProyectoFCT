package com.example.proyectofct.data.retrofit.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.proyectofct.data.retrofit.database.entities.FacturaEntity

@Dao
interface FacturaDao {
    @Query("SELECT * FROM factura_tabla")
    suspend fun getAllFacturas():List<FacturaEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(facturas:List<FacturaEntity>)

    @Query("DELETE FROM factura_tabla")
    suspend fun deleteAllFacturas()
}