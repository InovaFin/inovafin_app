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

        binding.btnConta.setOnClickListener{
            val i = Intent(this, ContaEscolhida::class.java);
            startActivity(i);
        }

        binding.btnAddContas.setOnClickListener{
            val i = Intent(this, NovaConta::class.java);
            startActivity(i);
        }

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }
}