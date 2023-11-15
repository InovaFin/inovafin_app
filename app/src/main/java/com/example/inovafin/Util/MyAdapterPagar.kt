package com.example.inovafin.Util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.R

class MyAdapterPagar(private val listaPagar : ArrayList<RegistroValorPagar>, private val itemClickListener: (RegistroValorPagar) -> Unit) : RecyclerView.Adapter<MyAdapterPagar.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.valor_pagar_item,
            parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return listaPagar.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = listaPagar[position]

        holder.nome.text = currentItem.nome
        holder.vencimento.text = currentItem.vencimento
        holder.valor.text = currentItem.valor
        holder.itemView.setOnClickListener { itemClickListener(currentItem) }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nome : TextView = itemView.findViewById(R.id.exibirNomePagar)
        val vencimento : TextView = itemView.findViewById(R.id.exibirDataPagar)
        val valor : TextView = itemView.findViewById(R.id.exibirValorPagar)
    }

}