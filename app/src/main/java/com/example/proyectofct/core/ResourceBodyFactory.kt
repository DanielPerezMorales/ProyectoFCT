package com.example.proyectofct.core

import android.R
import co.infinum.retromock.BodyFactory
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.InputStream


class ResourceBodyFactory : BodyFactory {
    override fun create(input: String): InputStream {
        return ResourceBodyFactory::class.java.getResourceAsStream("/raw/$input")
            ?: throw FileNotFoundException("Resource $input not found in raw folder")
    }
}
