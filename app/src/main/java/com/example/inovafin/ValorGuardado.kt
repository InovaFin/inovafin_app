package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityValorGuardadoBinding

class ValorGuardado : AppCompatActivity() {

    private lateinit var binding: ActivityValorGuardadoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityValorGuardadoBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            val navegarTelaAddValor = Intent (this, AdicionarValor::class.java)
            startActivity(navegarTelaAddValor)
        }

        binding.btRegistro.setOnClickListener {
            val navegarTelaRegistro = Intent (this, InfoRegistro::class.java)
            startActivity(navegarTelaRegistro)
        }
    }
}