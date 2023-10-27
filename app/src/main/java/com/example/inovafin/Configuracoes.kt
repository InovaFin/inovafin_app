package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityConfiguracoesBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Configuracoes : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracoesBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracoesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

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

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {

            val usuarioId = autentificacao.currentUser!!.uid

            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        val emailUsuario = autentificacao.currentUser!!.email
                        
                        binding.nomeUsuario.text = document.getString("nome")
                        binding.emailUsuario.text = emailUsuario

                        val foto = document.getString("foto")
                        // Verifique se a foto do banco não é nula antes de carregar
                        Glide.with(this).load(foto).into(binding.imagemUsuario)
                    }
                }
        }
    }

    fun deslogar() {
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