package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        fun inserir() {
            nome = binding.nomeUsuario.text.toString()
            email = binding.emailUsuario.text.toString()
            senha = binding.senhaUsuario.text.toString()

            url = Host

            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("nome", nome)
                    .setBodyParameter("email", email)
                    .setBodyParameter("senha", senha)
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        if (e != null) {
                            // Ocorreu um erro na solicitação HTTP
                            Toast.makeText(applicationContext, "Erro na solicitação: " + e.localizedMessage, Toast.LENGTH_LONG).show()
                        } else {
                            ret = result["status"].asString
                            if (ret == "ok")
                            {
                                Toast.makeText(applicationContext, "Incluído com sucesso", Toast.LENGTH_LONG).show()

                                // Navegação para tela Login
                                var voltarTela = Intent(this, Login::class.java)
                                startActivity(voltarTela)
                            }
                            else
                                Toast.makeText(applicationContext, "Erro", Toast.LENGTH_LONG).show()
                        }
                    })
            } catch (e: Exception) {
                // Lidar com exceções gerais aqui
                Toast.makeText(applicationContext, "Erro geral: " + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }

        // Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        // Navegação para Tela Home
        binding.btCadastro.setOnClickListener {
            inserir()
        }

    }
}