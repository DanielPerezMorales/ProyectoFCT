package com.example.proyectofct.di

import android.content.Context
import co.infinum.retromock.Retromock
import com.example.proyectofct.core.ResourceBodyFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitHelper {
    @Singleton
    @Provides
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://viewnextandroid4.wiremockapi.cloud/").addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Singleton
    @Provides
    fun getRetromock(context: Context): Retromock {
        val retrofit = getRetrofit()
        return Retromock.Builder().retrofit(retrofit).defaultBodyFactory(ResourceBodyFactory(context)).build()
    }
}