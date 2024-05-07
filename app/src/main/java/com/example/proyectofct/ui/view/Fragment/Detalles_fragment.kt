package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.databinding.FragmentDetallesFragmentBinding
import com.example.proyectofct.ui.viewmodel.DetallesViewModel

class Detalles_fragment : Fragment() {

    private lateinit var binding: FragmentDetallesFragmentBinding
    private val detallesObject = Detalles_Object
    private val alert = Alert()

    // ViewModel
    private val viewModel: DetallesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetallesFragmentBinding.inflate(layoutInflater)

        viewModel.detallesLiveData.observe(viewLifecycleOwner, { detalles ->
            implementarDatos(detalles)
        })

        viewModel.cargarDetalles(requireContext(),detallesObject)

        binding.btnInformation.setOnClickListener {
            alert.showPopNative(this)
        }

        return binding.root
    }

    private fun implementarDatos(detalles: Modelo_Detalles) {
        binding.etCAU.setText(detalles.CAU)
        binding.etEstado.setText(detalles.solicitud)
        binding.etExcedentes.setText(detalles.Excedentes)
        binding.etPotencia.setText(detalles.Potencia)
        binding.etAutoConsumo.setText(detalles.Tipo)

        binding.etCAU.isEnabled = false
        binding.etEstado.isEnabled = false
        binding.etExcedentes.isEnabled = false
        binding.etPotencia.isEnabled = false
        binding.etAutoConsumo.isEnabled = false
    }
}