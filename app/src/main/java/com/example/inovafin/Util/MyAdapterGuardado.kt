package com.example.inovafin.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.R

class MyAdapterGuardado(private val listaGuardado : ArrayList<RegistroValorGuardado>, private val itemClickListener: (RegistroValorGuardado) -> Unit) : RecyclerView.Adapter<MyAdapterGuardado.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.valor_guardado_item,
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
        holder.itemView.setOnClickListener { itemClickListener(currentItem) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nome : TextView = itemView.findViewById(R.id.exibirNomeGuardado)
        val valor : TextView = itemView.findViewById(R.id.exibirValorGuardado)
    }

}