package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.inovafin.databinding.ActivityEsqueceuSenhaBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class EsqueceuSenha : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/PHPMailer-master/recuperar.php"
    var url: String? = null
    var ret: String? = null

    var email: String? = null

    private lateinit var binding: ActivityEsqueceuSenhaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icVoltar.setOnClickListener {
            val voltarTela = Intent(this, Login::class.java)
            startActivity(voltarTela)
        }

        binding.btRecuperar.setOnClickListener {
            recuperar()
        }
    }
    fun recuperar() {
        email = binding.emailUsuario.text.toString()

        url = Host

        Ion.with(this)
            .load(url)
            .setBodyParameter("email", email)
            .asJsonObject()
            .setCallback(FutureCallback<JsonObject> { e, result ->
                if (e != null) {
                    // Ocorreu um erro na solicitação HTTP
                    Toast.makeText(applicationContext, "Erro na solicitação HTTP: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                } else {
                    try {
                        val jsonObject = result.asJsonObject
                        val ret = jsonObject.get("status").asString
                        if (ret == "ok") {
                            Toast.makeText(applicationContext, "Senha atualizada!\nVerifique sua caixa de entrada!", Toast.LENGTH_LONG).show()
                        } else {
                            Toast.makeText(applicationContext, "Erro no servidor: $ret", Toast.LENGTH_LONG).show()
                        }
                    } catch (ex: Exception) {
                        // Erro ao analisar o JSON da resposta do servidor
                        Toast.makeText(applicationContext, "Erro ao analisar resposta do servidor: ${ex.localizedMessage}", Toast.LENGTH_LONG).show()
                    }
                }
            })
    }
}