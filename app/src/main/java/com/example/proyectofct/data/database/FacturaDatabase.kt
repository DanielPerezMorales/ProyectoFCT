package com.example.proyectofct.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectofct.data.database.dao.Factura_dao
import com.example.proyectofct.data.database.entities.FacturaEntity

@Database(entities = [FacturaEntity::class],  version=1)
@TypeConverters(Converter::class)
abstract class FacturaDatabase:RoomDatabase() {

    abstract fun getFactureDao():Factura_dao
}