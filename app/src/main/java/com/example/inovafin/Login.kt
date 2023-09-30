package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Load.AnimacaoDeLoad
import com.example.inovafin.Validacoes.Validacao
import com.example.inovafin.databinding.ActivityLoginBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class Login : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/projeto/login.php"
    var url: String? = null
    var ret: String? = null

    var email: String? = null
    var senha: String? = null

    private lateinit var binding: ActivityLoginBinding

    // Armazena uma instância da classe AnimacaoDeLoad
    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Variáveis parâmetro para o métodos da classe Validacao e solicitação HTTP
        email = binding.emailUsuario.text.toString()
        senha = binding.senhaUsuario.text.toString()

        // Cria uma nova instância da classe AnimacaoDeLoad e inicializa ela com os parâmetros relevantes
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        //Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        binding.btEsqueceuSenha.setOnClickListener {
            var navegarTelaEsqSenha = Intent(this, EsqueceuSenha::class.java)
            startActivity(navegarTelaEsqSenha)
        }

        // Navegação para Tela Home
        binding.btLogin.setOnClickListener {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.iniciarAnimacao()

            logar()
        }
    }

    fun logar() {
        url = Host

        // Chama um método da classe Validacao e verifica seu valor
        if (Validacao.validarEmail(email!!)){
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("email", email)
                    .setBodyParameter("senha",senha)
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        // Chama um método da Classe AnimacaoDeLoad
                        animacaoDeLoad.pararAnimacao()

                        val jsonObject = result.asJsonObject
                        val ret = jsonObject.get("status").asString
                        if (ret == "ok")
                        {
                            Toast.makeText(applicationContext, "Logado com sucesso", Toast.LENGTH_LONG).show()

                            // Navegação para tela Home
                            var navegarTelaHome = Intent(this, Home::class.java)
                            startActivity(navegarTelaHome)
                        }
                        else
                            Toast.makeText(applicationContext, "$ret", Toast.LENGTH_LONG).show()
                    })
            } catch (e: Exception) {
                // Lidar com exceções gerais aqui
                Toast.makeText(applicationContext, "$ret", Toast.LENGTH_LONG).show()
            }
        }
        else {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.pararAnimacao()

            binding.emailUsuario.error = "Digite um Email válido!"
        }
    }
}