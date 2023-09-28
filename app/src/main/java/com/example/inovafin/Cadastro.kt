package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Patterns
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
    var confirmSenha: String? = null

    private lateinit var binding: ActivityCadastroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        binding.btCadastro.setOnClickListener {
            iniciarAnimacao()
            inserir()
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

    fun validarEmail(emailText: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(emailText).matches()
    }

    fun validarSenha(): String? {
        senha = binding.senhaUsuario.text.toString()
        if (senha!!.length >= 8) {
            if (senha == confirmSenha) {
                return null // Senha válida
            } else {
                return "As senhas são diferentes!"
            }
        } else {
            return "A senha deve ter pelo menos 8 caracteres"
        }
    }

    fun inserir() {
        nome = binding.nomeUsuario.text.toString()
        email = binding.emailUsuario.text.toString()
        confirmSenha = binding.confirmSenhaUsuario.text.toString()

        url = Host

        val erroSenha = validarSenha()

        if (validarEmail(email!!)){
            if (erroSenha === null){
                pararAnimacao()
                Toast.makeText(this, "Senhas corretas!", Toast.LENGTH_SHORT).show()
//                try {
//                    Ion.with(this)
//                        .load(url)
//                        .setBodyParameter("nome", nome)
//                        .setBodyParameter("email", email)
//                        .setBodyParameter("senha", senha)
//                        .asJsonObject()
//                        .setCallback(FutureCallback<JsonObject> { e, result ->
//                            pararAnimacao()
//                            if (e != null) {
//                                // Ocorreu um erro na solicitação HTTP
//                                Toast.makeText(applicationContext, "Erro ao cadastrar: " + e.localizedMessage, Toast.LENGTH_LONG).show()
//                            } else {
//                                ret = result["status"].asString
//                                if (ret == "ok")
//                                {
//                                    Toast.makeText(applicationContext, "Cadastro realizado!", Toast.LENGTH_LONG).show()
//
//                                    // Navegação para tela Login
//                                    var navegarTelaLogin = Intent(this, Login::class.java)
//                                    startActivity(navegarTelaLogin)
//                                }
//                                else
//                                    Toast.makeText(applicationContext, "$ret", Toast.LENGTH_LONG).show()
//                            }
//                        })
//                } catch (e: Exception) {
//                    // Lidar com exceções gerais aqui
//                    Toast.makeText(applicationContext, "Erro" + e.localizedMessage, Toast.LENGTH_LONG).show()
//                }
            }
            else if (erroSenha == "As senhas são diferentes!"){
                pararAnimacao()
                Toast.makeText(this, "$erroSenha", Toast.LENGTH_SHORT).show()
            }
            else if (erroSenha == "A senha deve ter pelo menos 8 caracteres"){
                pararAnimacao()
                Toast.makeText(this, "$erroSenha", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            pararAnimacao()
            Toast.makeText(this, "Email inválido!", Toast.LENGTH_SHORT).show()
        }
    }
}