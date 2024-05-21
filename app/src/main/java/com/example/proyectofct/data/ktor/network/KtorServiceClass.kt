package com.example.proyectofct.data.ktor.network

import android.util.Log
import com.example.proyectofct.data.ktor.HTTPRoutes
import com.example.proyectofct.data.ktor.model.factura_item_model
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.ClientRequestException
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


class KtorServiceClass : KtorService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint =true
                isLenient = true
            })
        }
        install(Logging) {
            level = LogLevel.ALL
            logger = Logger.DEFAULT
        }
    }

    override suspend fun getAllFacturas(): List<factura_item_model>? {
        var facturas: List<factura_item_model>? = null
        withContext(Dispatchers.IO) {
            try {
                facturas = client.get(HTTPRoutes.BASE_URL).body()
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