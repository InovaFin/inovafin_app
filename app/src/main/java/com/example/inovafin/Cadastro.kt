package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityCadastroBinding

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        // Navegação para Tela Home
//        binding.btCadastro.setOnClickListener {
//            var navegarTelaHome = Intent(this, Home::class.java)
//            startActivity(navegarTelaHome)
//        }
    }
}