package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityMinhasContasBinding

class MinhasContas : AppCompatActivity() {

    private lateinit var binding: ActivityMinhasContasBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMinhasContasBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
        //Pegando o btn adicionar conta
        val btnAddContas = findViewById<LinearLayout>(R.id.btnAddContas);

        //Evento do botao de adicionar contas
        btnAddContas.setOnClickListener{
            val intent = Intent(this, NovaConta::class.java);

            startActivity(intent);
        }
    }
}