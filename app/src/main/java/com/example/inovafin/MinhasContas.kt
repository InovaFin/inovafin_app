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

        binding.btnContaEscolhida1.setOnClickListener{
            val intent = Intent(this, ContaEscolhida::class.java);
            startActivity(intent);
        }

        binding.btnAddContas.setOnClickListener{
            val intent = Intent(this, NovaConta::class.java);

            startActivity(intent);
        }

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }
}