package com.example.inovafin

import com.example.inovafin.Util.AnimacaoDeLoad
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityEsqueceuSenhaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class EsqueceuSenha : AppCompatActivity() {

    private lateinit var binding: ActivityEsqueceuSenhaBinding

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var autentificacao: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        binding.icVoltar.setOnClickListener {
            onBackPressed()
        }

        binding.btRecuperar.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()

            validarCampo()
        }
    }

    fun validarCampo() {
        val email = binding.emailUsuario.text.toString()

        if (email.isNotEmpty()) {
            recuperarSenha()
        } else {
            animacaoDeLoad.pararAnimacao()
            Toast.makeText(applicationContext, "Preencha um Email", Toast.LENGTH_LONG).show()
        }
    }

    fun recuperarSenha() {
        val email = binding.emailUsuario.text.toString()
        autentificacao = ConfiguraBd.Firebaseautentificacao()

        autentificacao.sendPasswordResetEmail(email)
            .addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                animacaoDeLoad.pararAnimacao()

                var i = Intent(this, SenhaEnviada::class.java)
                startActivity(i)
            } else {
                animacaoDeLoad.pararAnimacao()

                var excecao = ""

                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "Email incorreto!"
                } catch (e: Exception) {
                    excecao = "Erro ao enviar email! " + e.message
                    e.printStackTrace()
                }

                Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
            }
        }

    }
}