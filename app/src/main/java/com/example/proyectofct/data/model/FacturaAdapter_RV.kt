package com.example.proyectofct.data.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.R

class FacturaAdapter_RV (var FacturaList: List<facturaItem> = emptyList(), private val onItemSelected: (String) -> Unit): RecyclerView.Adapter<FacturaViewHolder>(){
    fun updateList(List: List<facturaItem>) {
        FacturaList = List
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturaViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return FacturaViewHolder(layout.inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        return FacturaList.size
    }

    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        val item = FacturaList[position]
        holder.bind(item, onItemSelected)
    }
}