package com.example.proyectofct.data.model

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.databinding.ItemBinding

class FacturaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBinding.bind(view)

    fun bind(superHeroItemResponse: facturaItem, onItemSelected: (String) -> Unit) {
        binding.TVfechaFactura.text = superHeroItemResponse.fecha.toString()
        if(superHeroItemResponse.descEstado != "Pagada") {
            binding.TVestadoFactura.text = superHeroItemResponse.descEstado
        } else {
            binding.TVestadoFactura.visibility=View.GONE
        }
        var dinero = superHeroItemResponse.importeOrdenacion
        binding.TVDinero.text = "$dinero€"
        binding.root.setOnClickListener{
            onItemSelected(superHeroItemResponse.fecha.toString())
        }
    }
}
