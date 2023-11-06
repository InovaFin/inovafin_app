package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityContaPagarBinding

class ContaPagar : AppCompatActivity() {

    private lateinit var binding: ActivityContaPagarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContaPagarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            val i = Intent (this, AddReceber::class.java)
            startActivity(i)
        }

        binding.btRegistro.setOnClickListener {
            val i = Intent (this, InfoRegistro::class.java)
            startActivity(i)
        }

        binding.btExcluir.setOnClickListener {
            val i = Intent (this, ExcluirPagar::class.java)
            startActivity(i)
        }
    }
}