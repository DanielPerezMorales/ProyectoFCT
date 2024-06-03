package com.example.proyectofct.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object HTTPRoutesModule {
    @Singleton
    @Provides
    fun provideURL() = "http://172.16.219.14:8080/facturas"
}