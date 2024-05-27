package com.example.proyectofct.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import com.example.proyectofct.domain.DetallesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetallesViewModel @Inject constructor(private val detallesUseCase : DetallesUseCase): ViewModel() {

    private val _detallesLiveData = MutableLiveData<ModeloDetalles>()
    val detallesLiveData: LiveData<ModeloDetalles> = _detallesLiveData

    fun cargarDetalles() {
        CoroutineScope(Dispatchers.IO).launch {
            val detalles = detallesUseCase.obtenerDetalles()
            _detallesLiveData.postValue(detalles)
        }
    }
}