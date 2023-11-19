package com.example.inovafin.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.R

class MyAdapterSaldoGGuardado(private val listaGuardado : ArrayList<RegistroSaldoGGuardado>, private val itemClickListener: (RegistroSaldoGGuardado) -> Unit) : RecyclerView.Adapter<MyAdapterSaldoGGuardado.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.saldog_guardado_item,
            parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return listaGuardado.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = listaGuardado[position]

        holder.nome.text = currentItem.nome
        holder.valor.text = currentItem.valor

        // Defina a seleção com base no estado atual do item
        holder.itemView.isSelected = currentItem.isSelected

        holder.checkbox.visibility = if (currentItem.isSelected) View.GONE else View.VISIBLE
        holder.checkboxSelected.visibility = if (currentItem.isSelected) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            currentItem.isSelected = !currentItem.isSelected
            notifyItemChanged(position)
            itemClickListener(currentItem)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val checkbox: ImageView = itemView.findViewById(R.id.checkbox)
        val checkboxSelected: ImageView = itemView.findViewById(R.id.checkbox_selected)
        val nome : TextView = itemView.findViewById(R.id.exibirNomeReceber)
        val valor : TextView = itemView.findViewById(R.id.exibirValorReceber)
    }

}