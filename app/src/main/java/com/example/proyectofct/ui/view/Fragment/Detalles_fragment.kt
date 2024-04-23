package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.databinding.FragmentDetallesFragmentBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Detalles_fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Detalles_fragment : Fragment() {
    private lateinit var binding: FragmentDetallesFragmentBinding
    private val alert= Alert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetallesFragmentBinding.inflate(layoutInflater)
        val response =obtenerDatos()
        binding.btnInformation.setOnClickListener{
            alert.showPopNative(this)
        }
        binding.etCAU.setText(response.CAU)
        binding.etPotencia.setText(response.Potencia)
        binding.etAutoConsumo.setText(response.Tipo)
        binding.etExcedentes.setText(response.Excedentes)
        binding.etEstado.setText(response.solicitud)
        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Detalles_fragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun obtenerDatos():Modelo_Detalles{
        return Modelo_Detalles("ES0021000000001994LJ1FA000", "No hemos recibido ninguna solicitud de autoconsumo", "Con excendentes y compensacion individual Consumo", "Precio PVPC", "5kWp")
    }
}