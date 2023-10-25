package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityLoginCadastroBinding
import com.google.firebase.auth.FirebaseAuth

class LoginCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityLoginCadastroBinding

    private lateinit var autentificacao: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()

        // Navegação para Tela Login
        binding.btLogin.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }

        binding.btCadastro.setOnClickListener {
            var i = Intent(this, Cadastro::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser
        if (usuarioAuth != null) {
            var i = Intent(this, Home::class.java)
            startActivity(i)
        }
    }
}