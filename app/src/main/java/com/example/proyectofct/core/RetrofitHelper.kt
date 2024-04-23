package com.example.proyectofct.core

import android.content.Context
import co.infinum.retromock.Retromock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://viewnextandroid4.wiremockapi.cloud/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getRetromock(context: Context): Retromock {
        val retrofit = getRetrofit()
        return Retromock.Builder()
            .retrofit(retrofit)
            // Aquí puedes agregar configuraciones adicionales para Retromock, como la ruta base para las respuestas simuladas
            .defaultBodyFactory(ResourceBodyFactory(context))
            .build()
    }
}