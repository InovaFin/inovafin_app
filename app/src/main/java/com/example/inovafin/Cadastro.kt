package com.example.inovafin

import com.example.inovafin.Load.AnimacaoDeLoad
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.databinding.ActivityCadastroBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class Cadastro : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/projeto/inserir.php"
    var url: String? = null
    var ret: String? = null

    var nome: String? = null
    var email: String? = null
    var senha: String? = null
    var confirmSenha: String? = null

    private lateinit var binding: ActivityCadastroBinding
    private lateinit var animacaoDeLoad: AnimacaoDeLoad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        // Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        binding.btCadastro.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()
            inserir()
        }
    }

    fun validarNome(nome: String): Boolean {
        return nome.isNotEmpty() && nome.length >= 3
    }

    fun validarEmail(emailText: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(emailText).matches()
    }

    fun validarSenha(): Int? {
        senha = binding.senhaUsuario.text.toString()
        if (senha!!.length >= 8) {
            if (senha == confirmSenha) {
                return null // Senha válida
            } else {
                return 1
            }
        } else {
            return 0
        }
    }

    fun inserir() {
        nome = binding.nomeUsuario.text.toString()
        email = binding.emailUsuario.text.toString()
        confirmSenha = binding.confirmSenhaUsuario.text.toString()

        url = Host

        val erroSenha = validarSenha()

        if (validarNome(nome!!) && validarEmail(email!!) && erroSenha === null) {
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("nome", nome)
                    .setBodyParameter("email", email)
                    .setBodyParameter("senha", senha)
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        animacaoDeLoad.pararAnimacao()
                        if (e != null) {
                            // Ocorreu um erro na solicitação HTTP
                            Toast.makeText(applicationContext, "Erro ao cadastrar: " + e.localizedMessage, Toast.LENGTH_LONG).show()
                        } else {
                            ret = result["status"].asString
                            if (ret == "ok")
                            {
                                Toast.makeText(applicationContext, "Cadastro realizado!", Toast.LENGTH_LONG).show()

                                // Navegação para tela Login
                                var navegarTelaLogin = Intent(this, Login::class.java)
                                startActivity(navegarTelaLogin)
                            }
                            else
                                Toast.makeText(applicationContext, "$ret", Toast.LENGTH_LONG).show()
                        }
                    })
            } catch (e: Exception) {
                // Lidar com exceções gerais aqui
                Toast.makeText(applicationContext, "Erro" + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        else {
            animacaoDeLoad.pararAnimacao()
            if (!validarNome(nome!!)) {
                binding.nomeUsuario.error = "Nome inválido (pelo menos 3 caracteres)"
            }
            if (!validarEmail(email!!)) {
                binding.emailUsuario.error = "Email inválido"
            }
            if (erroSenha == 0){
                binding.senhaUsuario.error = "A senha deve conter no mínimo 8 caracteres!"
            }
            else
            {
                binding.confirmSenhaUsuario.error = "As senhas são diferentes!"
            }
        }
    }
}