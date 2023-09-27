package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

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
            iniciarAnimacao()
            logar()
        }
    }

    fun iniciarAnimacao() {
        // Tornar a animação visível e iniciar
        binding.btAnimacao.visibility = View.VISIBLE
        binding.btAnimacao.playAnimation()

        // Ocultar o texto do botão
        binding.btText.visibility = View.GONE
    }

    fun pararAnimacao() {
        binding.btAnimacao.cancelAnimation()
        binding.btAnimacao.visibility = View.GONE

        binding.btText.visibility = View.VISIBLE
    }

    fun logar() {
        email = binding.emailUsuario.text.toString()
        senha = binding.senhaUsuario.text.toString()

        url = Host

        try {
            Ion.with(this)
                .load(url)
                .setBodyParameter("email", email)
                .setBodyParameter("senha",senha)
                .asJsonObject()
                .setCallback(FutureCallback<JsonObject> { e, result ->
                    pararAnimacao()
                    val jsonObject = result.asJsonObject
                    val ret = jsonObject.get("status").asString
                    if (ret == "ok")
                    {
                        Toast.makeText(applicationContext, "Logado com sucesso", Toast.LENGTH_LONG).show()

//                      // Navegação para tela Home
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
}