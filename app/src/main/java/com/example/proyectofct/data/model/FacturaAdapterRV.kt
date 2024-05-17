package com.example.proyectofct.data.model

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectofct.R

class FacturaAdapterRV (private var facturaList: List<FacturaItem> = emptyList(), private val onItemSelected: (String) -> Unit): RecyclerView.Adapter<FacturaViewHolder>(){
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<FacturaItem>) {
        facturaList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacturaViewHolder {
        val layout = LayoutInflater.from(parent.context)
        return FacturaViewHolder(layout.inflate(R.layout.item, parent, false))
    }

    override fun getItemCount(): Int {
        return facturaList.size
    }

    override fun onBindViewHolder(holder: FacturaViewHolder, position: Int) {
        val item = facturaList[position]
        holder.bind(item, onItemSelected)
    }
}