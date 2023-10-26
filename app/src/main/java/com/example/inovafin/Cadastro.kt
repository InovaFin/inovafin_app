package com.example.inovafin

import android.content.Intent
import com.example.inovafin.Util.AnimacaoDeLoad
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityCadastroBinding
import com.example.inovafin.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var usuario: Usuario

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
            usuario = Usuario(nome, email, senha)
            cadastrarUsuario()
        } else {
            if (nome.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha um Nome", Toast.LENGTH_LONG).show()
            } else if (email.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha um Email", Toast.LENGTH_LONG).show()
            } else if (senha.isEmpty()) {
                Toast.makeText(applicationContext, "Preencha uma Senha", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun cadastrarUsuario() {
        autentificacao.createUserWithEmailAndPassword(
            usuario.email, usuario.senha
        ).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                animacaoDeLoad.pararAnimacao()

                salvaDados()

                var i = Intent(this, Login::class.java)
                startActivity(i)
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

    private fun salvaDados() {

        val usuarioMasp = hashMapOf(
            "foto" to "",
            "nome" to usuario.nome
        )

        val usuarioId = autentificacao.currentUser!!.uid

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