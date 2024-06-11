package com.example.proyectofct.di

import android.content.Context
import com.example.proyectofct.R
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
    fun provideURL(context: Context) = context.getString(R.string.Modulos_IP_KTOR)

    @LINK
    @Singleton
    @Provides
    fun provideLINK(context: Context) = context.getString(R.string.Modulos_linkIberdrola)
}