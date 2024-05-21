package com.example.proyectofct.data.ktor.network

import com.example.proyectofct.data.ktor.model.factura_item_model


interface KtorService {
    suspend fun getAllFacturas(): List<factura_item_model>?
}