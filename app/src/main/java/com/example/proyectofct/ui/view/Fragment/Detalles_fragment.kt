package com.example.proyectofct.ui.view.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.proyectofct.core.Alert
import com.example.proyectofct.data.mock.Mock
import com.example.proyectofct.data.model.Modelo_Detalles
import com.example.proyectofct.databinding.FragmentDetallesFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
    private lateinit var facturaserviceMock: Mock
    private val alert= Alert()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetallesFragmentBinding.inflate(layoutInflater)
        facturaserviceMock= Mock(requireContext())
        obtenerDatos(facturaserviceMock)
        binding.btnInformation.setOnClickListener{
            alert.showPopNative(this)
        }
        return binding.root
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

    private fun obtenerDatos(service: Mock){
        var modelo_detalles_mock:Modelo_Detalles?=null
        CoroutineScope(Dispatchers.IO).launch {
            val facturasMock = service.getDetallesMOCK()
            if (facturasMock != null) {
                modelo_detalles_mock=Modelo_Detalles(CAU = facturasMock.CAU, solicitud= facturasMock.solicitud, Tipo= facturasMock.Tipo, Excedentes = facturasMock.Excedentes, Potencia = facturasMock.Potencia)
                binding.etCAU.setText(modelo_detalles_mock!!.CAU)
                binding.etPotencia.setText(modelo_detalles_mock!!.Potencia)
                binding.etAutoConsumo.setText(modelo_detalles_mock!!.Tipo)
                binding.etExcedentes.setText(modelo_detalles_mock!!.Excedentes)
                binding.etEstado.setText(modelo_detalles_mock!!.solicitud)

                binding.etCAU.isEnabled=false
                binding.etPotencia.isEnabled=false
                binding.etAutoConsumo.isEnabled=false
                binding.etExcedentes.isEnabled=false
                binding.etEstado.isEnabled=false
                Log.i("TAG", "DATOS INTRODUCIDOS POR MOCK")
            }
        }
    }
}