package com.example.proyectofct.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.domain.DetallesUseCase
import kotlinx.coroutines.launch

class DetallesViewModel: ViewModel() {

    private val _detallesLiveData = MutableLiveData<Modelo_Detalles>()
    val detallesLiveData: LiveData<Modelo_Detalles> = _detallesLiveData

    fun cargarDetalles(context: Context, detalles:Detalles_Object) {
        val detallesUseCase = DetallesUseCase(detalles)
        viewModelScope.launch {
            val detalles = detallesUseCase.obtenerDetalles(context)
            _detallesLiveData.postValue(detalles)
        }
    }
}