package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.databinding.ActivityLoginCadastroBinding

class LoginCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityLoginCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Navegação para Tela Login
        binding.btLogin.setOnClickListener {
            var navegarTelaLogin = Intent(this, Login::class.java)
            startActivity(navegarTelaLogin)
        }

        binding.btCadastro.setOnClickListener {
            var navegarTelaCadastro = Intent(this, Cadastro::class.java)
            startActivity(navegarTelaCadastro)
        }
    }
}