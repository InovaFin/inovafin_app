package com.example.inovafin

import android.content.Intent
import com.example.inovafin.Util.AnimacaoDeLoad
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityCadastroBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        // Cria uma nova instância da classe AnimacaoDeLoad e inicializa ela com os parâmetros relevantes
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        binding.btCadastro.setOnClickListener {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.iniciarAnimacao()

            validarCampos()
        }

        binding.btLogin.setOnClickListener {
            var i = Intent(this, Login::class.java)
            startActivity(i)
        }
    }

    fun validarCampos() {
        val nome = binding.nomeUsuario.text.toString()
        val email = binding.emailUsuario.text.toString()
        val senha = binding.senhaUsuario.text.toString()

        if (nome.isNotEmpty() && email.isNotEmpty() && senha.isNotEmpty()) {
            cadastrarUsuario()
        } else {
            animacaoDeLoad.pararAnimacao()
            if (nome.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha um Nome", Toast.LENGTH_LONG).show()
                Log.d("d", "Sem nome")
            } else if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha um Email", Toast.LENGTH_LONG).show()
            } else if (senha.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha uma Senha", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun cadastrarUsuario() {
        val email = binding.emailUsuario.text.toString()
        val senha = binding.senhaUsuario.text.toString()

        autentificacao.createUserWithEmailAndPassword(
            email, senha
        ).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                animacaoDeLoad.pararAnimacao()

                salvaDados()

                verificarEmail()
            } else {
                animacaoDeLoad.pararAnimacao()

                var excecao = ""

                try {
                    throw task.exception!!
                } catch (e: FirebaseAuthWeakPasswordException) {
                    excecao = "Digite uma senha mais forte!"
                } catch (e: FirebaseAuthInvalidCredentialsException) {
                    excecao = "Digite um Email válido!"
                } catch (e: FirebaseAuthUserCollisionException) {
                    excecao = "Essa conta já existe!"
                } catch (e: Exception) {
                    excecao = "Erro ao cadastrar usuário! " + e.message
                    e.printStackTrace()
                }

                Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun verificarEmail() {
        val usuarioAuth = autentificacao.currentUser

        // Envia um email de verificação para o usuario
        usuarioAuth!!.sendEmailVerification().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                var i = Intent(this, EmailEnviado::class.java)
                startActivity(i)
            }
            else {
                Toast.makeText(applicationContext, "Erro ao enviar email de verificação", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun salvaDados() {
        val nome = binding.nomeUsuario.text.toString()
        val usuarioId = autentificacao.currentUser!!.uid
        val email = autentificacao.currentUser!!.email

        val usuarioMasp = hashMapOf(
            "foto" to "",
            "nome" to nome,
            "email" to email,
            "usuarioID" to usuarioId
        )

        firestore.collection("Usuarios").document(usuarioId)
            .set(usuarioMasp).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {

            } else {
                var excecao = ""

                try {
                    throw task.exception!!
                } catch (e: Exception) {
                    excecao = "Erro ao salvar os dados! " + e.message
                    e.printStackTrace()
                }
                Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        // Impede que o usuário volte para a tela anterior
    }
}