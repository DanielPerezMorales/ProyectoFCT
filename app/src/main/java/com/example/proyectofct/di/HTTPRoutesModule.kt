package com.example.proyectofct.di

import com.example.proyectofct.core.LINK
import com.example.proyectofct.core.URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object HTTPRoutesModule {
    @URL
    @Singleton
    @Provides
    fun provideURL() = "http://172.16.219.14:8080/facturas"

    @LINK
    @Singleton
    @Provides
    fun provideLINK() = "https://www.iberdrola.es"
}