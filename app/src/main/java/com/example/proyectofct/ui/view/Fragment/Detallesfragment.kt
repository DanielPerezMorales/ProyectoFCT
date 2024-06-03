package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.DetallesObject
import com.example.proyectofct.data.retrofit.model.ModeloDetalles
import com.example.proyectofct.databinding.FragmentDetallesFragmentBinding
import com.example.proyectofct.ui.viewmodel.DetallesViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class Detallesfragment @Inject constructor(private val alert : Alert): Fragment() {

    private lateinit var binding: FragmentDetallesFragmentBinding

    private val viewModel: DetallesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetallesFragmentBinding.inflate(layoutInflater)

        viewModel.detallesLiveData.observe(viewLifecycleOwner) { detalles ->
            implementarDatos(detalles)
        }

        viewModel.cargarDetalles()

        binding.btnInformation.setOnClickListener {
            alert.showPopNative(this)
        }

        return binding.root
    }

    private fun implementarDatos(detalles: ModeloDetalles) {
        binding.etCAU.setText(detalles.cau)
        binding.etEstado.setText(detalles.solicitud)
        binding.etExcedentes.setText(detalles.excedentes)
        binding.etPotencia.setText(detalles.potencia)
        binding.etAutoConsumo.setText(detalles.tipo)

        binding.etCAU.isEnabled = false
        binding.etEstado.isEnabled = false
        binding.etExcedentes.isEnabled = false
        binding.etPotencia.isEnabled = false
        binding.etAutoConsumo.isEnabled = false
    }
}