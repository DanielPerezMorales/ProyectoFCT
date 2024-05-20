package com.example.proyectofct.data.retrofit

import android.content.Context
import co.infinum.retromock.Retromock
import com.example.proyectofct.core.ResourceBodyFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://viewnextandroid4.wiremockapi.cloud/").addConverterFactory(GsonConverterFactory.create()).build()
    }

    fun getRetromock(context: Context): Retromock {
        val retrofit = getRetrofit()
        return Retromock.Builder().retrofit(retrofit).defaultBodyFactory(ResourceBodyFactory(context)).build()
    }
}