package com.example.proyectofct.core

import android.content.Context
import co.infinum.retromock.BodyFactory
import java.io.InputStream

class ResourceBodyFactory (private val context: Context): BodyFactory {
    override fun create(input: String): InputStream = context.assets.open(input)
}

