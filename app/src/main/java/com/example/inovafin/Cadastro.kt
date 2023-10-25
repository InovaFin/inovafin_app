package com.example.inovafin

import com.example.inovafin.Util.AnimacaoDeLoad
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityCadastroBinding
import com.example.inovafin.model.Usuario
import com.google.firebase.auth.FirebaseAuth

class Cadastro : AppCompatActivity() {

    private lateinit var binding: ActivityCadastroBinding

    // Armazena uma instância da classe AnimacaoDeLoad
    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private lateinit var usuario: Usuario

    private lateinit var autentificacao: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cria uma nova instância da classe AnimacaoDeLoad e inicializa ela com os parâmetros relevantes
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        binding.icVoltar.setOnClickListener {
            onBackPressed()
        }

        binding.btCadastro.setOnClickListener {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.iniciarAnimacao()

            validarCampos()
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
        autentificacao = ConfiguraBd.Firebaseautentificacao()

        autentificacao.createUserWithEmailAndPassword(
            usuario.email, usuario.senha
        ).addOnCompleteListener(this) {task ->
            if (task.isSuccessful) {
                animacaoDeLoad.pararAnimacao()

                // O usuário foi criado com sucesso
                val user = task.result?.user
                Toast.makeText(applicationContext, "Usuário cadastrado!", Toast.LENGTH_LONG).show()
            } else {
                animacaoDeLoad.pararAnimacao()

                // Ocorreu um erro durante a criação do usuário
                val exception = task.exception
                Toast.makeText(applicationContext, "$exception", Toast.LENGTH_LONG).show()
            }
        }
    }
}