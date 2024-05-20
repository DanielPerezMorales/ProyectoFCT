package com.example.proyectofct.data.ktor.network

import com.example.proyectofct.data.ktor.HTTPRoutes
import com.example.proyectofct.data.ktor.model.factura_item_model
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


class KtorServiceClass :KtorService{
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.BODY
        }
    }

    fun getFacturas(): List<factura_item_model>? {
        var facturas: List<factura_item_model>? = null
        CoroutineScope(Dispatchers.IO).launch {
            val responseBody: String = client.get("https://${HTTPRoutes.FACTURAS}").bodyAsText()
            facturas = Json.decodeFromString(responseBody)
        }
        return facturas
    }

    override fun getAllFacturas(): List<factura_item_model> {
        TODO("Not yet implemented")
    }
}