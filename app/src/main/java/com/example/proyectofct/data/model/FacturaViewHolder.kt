package com.example.proyectofct.data.model

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.R
import com.example.proyectofct.databinding.ItemBinding

class FacturaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemBinding.bind(view)

    fun bind(superHeroItemResponse: facturaItem, onItemSelected: (String) -> Unit) {
        binding.TVfechaFactura.text = superHeroItemResponse.fecha
        binding.TVestadoFactura.text = superHeroItemResponse.descEstado

        if(superHeroItemResponse.descEstado == "Pagada") {
            binding.TVestadoFactura.visibility=View.GONE
        } else {
            binding.TVestadoFactura.visibility = View.VISIBLE
            val colorConsumo = ContextCompat.getColor(itemView.context, R.color.color_estado_factura)
            binding.TVestadoFactura.setTextColor(colorConsumo)
        }

        var dinero = superHeroItemResponse.importeOrdenacion
        binding.TVDinero.text = "$dinero€"
        binding.root.setOnClickListener{
            onItemSelected(superHeroItemResponse.fecha)
        }
        Log.i("BIND","EL ESTADO ES: ${superHeroItemResponse.descEstado}, LA FECHA ES: ${superHeroItemResponse.fecha}, PRECIO ES: ${superHeroItemResponse.importeOrdenacion}")
    }

}
