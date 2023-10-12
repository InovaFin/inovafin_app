package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityAdicionarValorBinding

class AdicionarValor : AppCompatActivity() {

    private lateinit var binding: ActivityAdicionarValorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdicionarValorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }
}