package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityConfiguracoesBinding
import com.google.firebase.auth.FirebaseAuth

class Configuracoes : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracoesBinding

    private lateinit var autentificacao: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btEditarPerfil.setOnClickListener {
            val i = Intent(this, EditarPerfil::class.java)
            startActivity(i)
        }

        binding.btSair.setOnClickListener {
            deslogar()
        }
    }

    fun deslogar() {
        autentificacao = ConfiguraBd.Firebaseautentificacao()
        try {
            autentificacao.signOut()
            finish()
            val i = Intent(this, SplashScreen::class.java)
            startActivity(i)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}