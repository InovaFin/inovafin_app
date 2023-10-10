package com.example.inovafin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityAjudaSaldoGeralBinding

class AjudaSaldoGeral : AppCompatActivity() {

    private lateinit var binding: ActivityAjudaSaldoGeralBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAjudaSaldoGeralBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
            // testanto
            // testando nova branch
        }
    }
}