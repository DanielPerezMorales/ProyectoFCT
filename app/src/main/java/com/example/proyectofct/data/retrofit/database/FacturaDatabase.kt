package com.example.proyectofct.data.retrofit.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.proyectofct.data.retrofit.database.dao.FacturaDao
import com.example.proyectofct.data.retrofit.database.entities.FacturaEntity

@Database(entities = [FacturaEntity::class],  version=1)
@TypeConverters(Converter::class)
abstract class FacturaDatabase:RoomDatabase() {

    abstract fun getFactureDao(): FacturaDao
}