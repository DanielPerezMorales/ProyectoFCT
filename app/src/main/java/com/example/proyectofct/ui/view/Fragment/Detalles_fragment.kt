package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofct.core.Alert
import com.example.proyectofct.core.Detalles_Object
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.databinding.FragmentDetallesFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Detalles_fragment : Fragment() {
    private lateinit var binding: FragmentDetallesFragmentBinding
    private val Detalles=Detalles_Object
    private val alert= Alert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetallesFragmentBinding.inflate(layoutInflater)
        Detalles.inicializar(requireContext())
        CoroutineScope(Dispatchers.IO).launch {
            implementarDatos(Detalles.obtenerInstancia())
        }
        binding.btnInformation.setOnClickListener{
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


        binding.etCAU.isEnabled=false
        binding.etEstado.isEnabled=false
        binding.etExcedentes.isEnabled=false
        binding.etPotencia.isEnabled=false
        binding.etAutoConsumo.isEnabled=false
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Detalles_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}