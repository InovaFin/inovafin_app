package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
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
            iniciarAnimacao()
            recuperar()
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

    fun validacaoEmail(emailText: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(emailText).matches()
    }

    fun recuperar() {
        email = binding.emailUsuario.text.toString()

        url = Host

        if (validacaoEmail(email!!)){
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("email", email)
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        pararAnimacao()
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
            catch (ex: Exception){
                Toast.makeText(applicationContext,"${ex.localizedMessage}", Toast.LENGTH_LONG).show()
            }
        }
        else {
            pararAnimacao()
            Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show()
        }
    }
}