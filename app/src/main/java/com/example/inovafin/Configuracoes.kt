package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityConfiguracoesBinding

class Configuracoes : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracoesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btEditarPerfil.setOnClickListener {
            val navegarTelaEditarPerfil = Intent(this, EditarPerfil::class.java)
            startActivity(navegarTelaEditarPerfil)
        }
    }
}