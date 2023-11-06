package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityValorPagarBinding

class ValorPagar : AppCompatActivity() {

    private lateinit var binding: ActivityValorPagarBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValorPagarBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            val navegarTelaAddValor = Intent (this, AddReceber::class.java)
            startActivity(navegarTelaAddValor)
        }

        binding.btRegistro.setOnClickListener {
            val navegarTelaRegistro = Intent (this, InfoRegistro::class.java)
            startActivity(navegarTelaRegistro)
        }

        binding.btExcluir.setOnClickListener {
            val navegarTelaExcluir = Intent (this, ExcluirPagar::class.java)
            startActivity(navegarTelaExcluir)
        }
    }
}