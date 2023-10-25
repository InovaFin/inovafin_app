package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.AnimacaoDeLoad
import com.example.inovafin.Util.Validacao
import com.example.inovafin.databinding.ActivityLoginBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class Login : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/projeto/login.php"
    var url: String? = null
    var ret: String? = null

    private lateinit var binding: ActivityLoginBinding

    // Armazena uma instância da classe AnimacaoDeLoad
    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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

        // Retorna um valor do metodo correspondente indicando o status do dado inserido pelo usuário
        val emailValido = Validacao.validarEmail(binding.emailUsuario.text.toString())

        // Chama um método da classe Validacao e verifica seu valor
        if (emailValido){
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("email", binding.emailUsuario.text.toString())
                    .setBodyParameter("senha", binding.senhaUsuario.text.toString())
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        // Chama um método da Classe AnimacaoDeLoad
                        animacaoDeLoad.pararAnimacao()

                        val jsonObject = result.asJsonObject
                        val ret = jsonObject.get("status").asString
                        if (ret == "ok")
                        {
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