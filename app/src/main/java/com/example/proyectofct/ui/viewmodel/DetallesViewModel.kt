package com.example.proyectofct.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import com.example.proyectofct.domain.DetallesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetallesViewModel: ViewModel() {

    private val _detallesLiveData = MutableLiveData<ModeloDetalles>()
    val detallesLiveData: LiveData<ModeloDetalles> = _detallesLiveData

    fun cargarDetalles(context: Context, detalles:DetallesObject) {
        val detallesUseCase = DetallesUseCase(detalles)
        CoroutineScope(Dispatchers.IO).launch {
            val detalles = detallesUseCase.obtenerDetalles(context)
            _detallesLiveData.postValue(detalles)
        }
    }
}