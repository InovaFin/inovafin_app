package com.example.inovafin.Util

import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.inovafin.R

class MyAdapter(private val listaConta : ArrayList<ContaBancaria>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.conta_item,
            parent, false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return listaConta.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = listaConta[position]

        holder.nome.text = currentItem.nome
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val nome : TextView = itemView.findViewById(R.id.nomeConta)

    }

}