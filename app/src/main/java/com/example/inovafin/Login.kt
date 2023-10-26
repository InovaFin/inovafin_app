package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.AnimacaoDeLoad
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityLoginBinding
import com.example.inovafin.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.FirebaseUser
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var usuario: Usuario

    private lateinit var autentificacao: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()

        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        binding.btEsqueceuSenha.setOnClickListener {
            var i = Intent(this, EsqueceuSenha::class.java)
            startActivity(i)
        }

        binding.btLogin.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()

            validarCampos()
        }

        binding.btCadastro.setOnClickListener {
            var i = Intent(this, Cadastro::class.java)
            startActivity(i)
        }
    }

    fun validarCampos() {
        val nome = ""
        val email = binding.emailUsuario.text.toString()
        val senha = binding.senhaUsuario.text.toString()

        if (email.isNotEmpty() && senha.isNotEmpty()) {
            usuario = Usuario(nome, email, senha)
            logarUsuario()
        } else {
            if (email.isEmpty()) {
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "Preencha um Email", Toast.LENGTH_LONG).show()
            } else if (senha.isEmpty()) {
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "Preencha uma Senha", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun logarUsuario() {
        autentificacao.signInWithEmailAndPassword(
            usuario.email, usuario.senha
        ).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                animacaoDeLoad.pararAnimacao()

                var i = Intent(this, Home::class.java)
                startActivity(i)
            } else {
                animacaoDeLoad.pararAnimacao()

                var excecao = ""

                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthInvalidUserException) {
                    excecao = "Usuario não cadastrado!"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "Email ou senha incorretos!"
                } catch (e: Exception) {
                    excecao = "Erro ao logar usuário! " + e.message
                    e.printStackTrace()
                }

                Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
            }
        }
    }
}