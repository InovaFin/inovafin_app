package com.example.inovafin

import com.example.inovafin.Load.AnimacaoDeLoad
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.inovafin.Validacoes.Validacao
import com.example.inovafin.databinding.ActivityEsqueceuSenhaBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class EsqueceuSenha : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/PHPMailer-master/recuperar.php"
    var url: String? = null
    var ret: String? = null

    private lateinit var binding: ActivityEsqueceuSenhaBinding

    // Armazena uma instância da AnimacaoDeLoad
    private lateinit var animacaoDeLoad: AnimacaoDeLoad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEsqueceuSenhaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cria uma nova instância da classe AnimacaoDeLoad e inicializa ela com os parâmetros relevantes
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        binding.icVoltar.setOnClickListener {
            val voltarTela = Intent(this, Login::class.java)
            startActivity(voltarTela)
        }

        binding.btRecuperar.setOnClickListener {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.iniciarAnimacao()

            recuperar()
        }
    }

    fun recuperar() {
        url = Host

        // Retorna um valor do metodo correspondente indicando o status do dado inserido pelo usuário
        val emailValido = Validacao.validarEmail(binding.emailUsuario.text.toString())

        if (emailValido){
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("email", binding.emailUsuario.text.toString())
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        // Chama um método da Classe AnimacaoDeLoad
                        animacaoDeLoad.pararAnimacao()

                        if (e != null) {
                            // Ocorreu um erro na solicitação HTTP
                            Toast.makeText(applicationContext, "Erro na solicitação HTTP: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                        } else {
                            try {
                                val jsonObject = result.asJsonObject
                                ret = jsonObject.get("status").asString
                                if (ret == "ok") {
                                    Toast.makeText(applicationContext, "Verifique sua caixa de entrada!", Toast.LENGTH_LONG).show()

                                    var navegacaoTelaSenhaEnviada = Intent(this, SenhaEnviada::class.java)
                                    startActivity(navegacaoTelaSenhaEnviada)
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
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.pararAnimacao()

            binding.emailUsuario.error = "Digite um Email válido!"
        }
    }
}