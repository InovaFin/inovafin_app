package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
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
            dialogConfirmacao()
        }
    }

    fun dialogConfirmacao() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmação")
        builder.setMessage("Tem certeza de que deseja sair do aplicativo?")

        builder.setPositiveButton("Sim") { dialog, which ->
            // Usuário confirmou a saída
            deslogar()
        }

        builder.setNegativeButton("Não") { dialog, which ->
            // Usuário cancelou a saída
        }

        val dialog = builder.create()
        dialog.show()
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