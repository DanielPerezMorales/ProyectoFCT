package com.example.proyectofct.data.ktor.network

import android.util.Log
import com.example.proyectofct.core.URL
import com.example.proyectofct.data.ktor.model.FacturaItemModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import javax.inject.Inject


class KtorServiceClass @Inject constructor(
    @URL private val url: String
) : KtorService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
    }

    override suspend fun getAllFacturas(): List<FacturaItemModel>? {
        var facturas: List<FacturaItemModel>? = null
        withContext(Dispatchers.IO) {
            try {
                facturas = client.get(url).body()
                Log.i("KTOR", "$facturas")
            } catch (e: ClientRequestException) {
                // Esto captura errores 4xx
                println("ClientRequestException: ${e.response.status}")
            } catch (e: ServerResponseException) {
                // Esto captura errores 5xx
                println("ServerResponseException: ${e.response.status}")
            } catch (e: Exception) {
                // Esto captura cualquier otro tipo de excepción
                println("Exception: ${e.message}")
            }
        }
        return facturas
    }
}