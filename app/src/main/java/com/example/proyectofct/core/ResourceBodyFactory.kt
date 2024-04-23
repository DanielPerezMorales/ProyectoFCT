package com.example.proyectofct.core

import android.content.Context
import android.util.Log
import co.infinum.retromock.BodyFactory
import java.io.IOException
import java.io.InputStream

class ResourceBodyFactory (private val context: Context): BodyFactory {

    override fun create(input: String): InputStream {
        return context.assets.open(input)
    }
}

