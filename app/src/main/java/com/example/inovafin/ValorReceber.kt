package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityValorReceberBinding

class ValorReceber : AppCompatActivity() {

    private lateinit var binding: ActivityValorReceberBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValorReceberBinding.inflate(layoutInflater)
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
            val navegarTelaExcluir = Intent (this, ExcluirReceber::class.java)
            startActivity(navegarTelaExcluir)
        }
    }
}