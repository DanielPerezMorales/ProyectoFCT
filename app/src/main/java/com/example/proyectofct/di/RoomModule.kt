package com.example.proyectofct.di

import android.content.Context
import androidx.room.Room
import com.example.proyectofct.data.database.FacturaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    private const val NAME_TABLE="factura_tabla"
    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) = Room.databaseBuilder(context, FacturaDatabase::class.java, NAME_TABLE).build()
    @Singleton
    @Provides
    fun provideFactureDao(db: FacturaDatabase)=db.getFactureDao()
}