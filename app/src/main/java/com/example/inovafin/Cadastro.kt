package com.example.inovafin

import com.example.inovafin.Load.AnimacaoDeLoad
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Validacoes.Validacao
import com.example.inovafin.databinding.ActivityCadastroBinding
import com.google.gson.JsonObject
import com.koushikdutta.async.future.FutureCallback
import com.koushikdutta.ion.Ion

class Cadastro : AppCompatActivity() {

    var Host = "https://inovafin.000webhostapp.com/projeto/inserir.php"
    var url: String? = null
    var ret: String? = null

    private lateinit var binding: ActivityCadastroBinding

    // Armazena uma instância da classe AnimacaoDeLoad
    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCadastroBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Cria uma nova instância da classe AnimacaoDeLoad e inicializa ela com os parâmetros relevantes
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        // Navegação para Tela LoginCadastro
        binding.icVoltar.setOnClickListener {
            var voltarTela = Intent(this, LoginCadastro::class.java)
            startActivity(voltarTela)
        }

        binding.btCadastro.setOnClickListener {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.iniciarAnimacao()

            inserir()
        }
    }

    fun inserir() {
        url = Host

        // Cada variável retorna um valor do metodo correspondente indicando o status do dado inserido pelo usuário
        val nomeValido = Validacao.validarNome(binding.nomeUsuario.text.toString())
        val emailValido = Validacao.validarEmail(binding.emailUsuario.text.toString())
        val erroSenha = Validacao.validarSenha(binding.senhaUsuario.text.toString(), binding.confirmSenhaUsuario.text.toString())

        // Chama dois métodos da classe Validacao mais uma uma variável e verifica seus valores
        if (nomeValido && emailValido && erroSenha === null) {
            try {
                Ion.with(this)
                    .load(url)
                    .setBodyParameter("nome", binding.nomeUsuario.text.toString())
                    .setBodyParameter("email", binding.emailUsuario.text.toString())
                    .setBodyParameter("senha", binding.senhaUsuario.text.toString())
                    .asJsonObject()
                    .setCallback(FutureCallback<JsonObject> { e, result ->
                        // Chama um método da Classe AnimacaoDeLoad
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
                Toast.makeText(applicationContext, "Erro" + e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
        else {
            // Chama um método da Classe AnimacaoDeLoad
            animacaoDeLoad.pararAnimacao()

            // Verifica os resultados da validação e mostra mensagens de erro apropriadas
            if (!nomeValido) {
                binding.nomeUsuario.error = "Nome inválido (pelo menos 3 caracteres)"
            }
            if (!emailValido) {
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