package com.example.proyectofct.data.ktor.network

import com.example.proyectofct.data.ktor.model.FacturaItemModel


interface KtorService {
    suspend fun getAllFacturas(): List<FacturaItemModel>?
}