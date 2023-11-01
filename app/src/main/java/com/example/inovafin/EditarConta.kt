package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.inovafin.Util.AnimacaoDeLoad
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityEditarContaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class EditarConta : AppCompatActivity() {

    private lateinit var binding: ActivityEditarContaBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private var numberFormat = NumberFormat.getCurrencyInstance()

    private var instituicaoAlterada = false
    private var saldoAtualFinal: Double = 0.0
    private var nomeFinal: String =""

    private var contaId: String = ""
    private var nomeConta: String = ""
    private var instituicao: String = ""
    private var saldoConta: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarContaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        contaId = intent.getStringExtra("contaId").toString()
        instituicao = intent.getStringExtra("instituicao").toString()
        saldoConta = intent.getStringExtra("saldoConta").toString()

        resgatarNome()

        // Insere o $nomeConta no EditText
        binding.nomeConta.hint = nomeConta

        formatandoSaldo()
        spinnerInstituicoes()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()
            validarCampos()
        }
    }

    private fun spinnerInstituicoes() {
        // Criando o array com opções
        val spinnerOP = listOf(
            "Selecione uma opção", "Banco do Brasil", "Banco Inter", "Banco Safra", "Bradesco", "BTG Pactual",
            "Caixa", "C6 Bank", "Itaú", "Mercado Pago", "Nubank", "PagBank", "PicPay",
            "Santander", "Sicoob", "Outro"
        )

        // Criando um adapter para o spinner
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter
    }

    private fun verificarSpinner() {
        val valorSpinner = binding.spinner.selectedItem

        instituicaoAlterada = valorSpinner != "Selecione uma opção"
    }

    private fun formatandoSaldo() {
        val editTextMonetario = binding.saldoAtual

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText(saldoConta)

        // Posicione o cursor no final do texto
        editTextMonetario.setSelection(editTextMonetario.text.length)

        // Adicionando o MoneyTextWatcher ao Saldo Atual
        editTextMonetario.addTextChangedListener(MoneyTextWatcher(editTextMonetario))
    }

    inner class MoneyTextWatcher(private val editText: EditText) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            editText.removeTextChangedListener(this)

            try {
                val initialString = s.toString()

                // Limpar o formato monetário anterior (remover vírgulas, pontos e símbolos de moeda)
                val cleanString = initialString.replace(Regex("[^\\d]"), "")

                val parsed = cleanString.toBigDecimal().divide(BigDecimal(100))

                val formatted = numberFormat.format(parsed)
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            } catch (e: Exception) {
                // Tratar exceção, se necessário
            }

            editText.addTextChangedListener(this)
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Não é necessário implementar
        }

    }

    private fun verificarSaldo() {
        val saldoAtual = binding.saldoAtual.text.toString()

        // Remova o símbolo da moeda, nesse caso "R$ "
        val saldoSemSimbolo = saldoAtual.replace(NumberFormat.getCurrencyInstance().currency.symbol, "")

        // Remova todos os caracteres que não são dígitos (0-9), incluindo pontos e vírgulas
        val saldoSemFormatacao = saldoSemSimbolo.replace(Regex("[^\\d]"), "")

        saldoAtualFinal = saldoSemFormatacao.toDouble() / 100.0
    }

    private fun validarCampos() {
        verificarSpinner()
        verificarSaldo()
        if (instituicaoAlterada) {
            if (saldoAtualFinal > 0.0){
                alterarConta()
            } else {
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "O saldo deve ser maior que R$ 0,00", Toast.LENGTH_LONG).show()
            }
        } else {
            animacaoDeLoad.pararAnimacao()
            Toast.makeText(applicationContext, "Selecione uma instituição", Toast.LENGTH_LONG).show()
        }
    }

    private fun alterarConta() {
        val usuarioId = autentificacao.currentUser!!.uid

        val nome = binding.nomeConta.text.toString()

        // Verifica se deve alterar ou manter o nome da conta
        if (nome != "") {
            nomeFinal = nome
        } else {
            nomeFinal = nomeConta
        }

        val instituicao = binding.spinner.selectedItem

        val usuarioMasp = hashMapOf(
            "nome" to nomeFinal,
            "instituicao" to instituicao,
            "saldo" to saldoAtualFinal
        )

        try {
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document(contaId)
                .update(usuarioMasp).addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        animacaoDeLoad.pararAnimacao()
                        Toast.makeText(applicationContext, "Conta alterada", Toast.LENGTH_LONG).show()
                        val i = Intent(this, MinhasContas::class.java)
                        startActivity(i)
                    } else {
                        animacaoDeLoad.pararAnimacao()
                        var excecao = ""

                        try {
                            throw task.exception!!
                        } catch (e: Exception) {
                            excecao = "Erro ao alterar conta" + e.message
                            e.printStackTrace()
                        }
                        animacaoDeLoad.pararAnimacao()
                        Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            animacaoDeLoad.pararAnimacao()
            Toast.makeText(applicationContext, "$e", Toast.LENGTH_LONG).show()
        }
    }

    private fun resgatarNome() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document(contaId)
            .addSnapshotListener { document, error ->
                if (document != null) {

                    val nomeResgatado = document.getString("nome")
                    nomeConta = nomeResgatado.toString()

                } else {
                    Toast.makeText(applicationContext, "Erro ao resgatar nome", Toast.LENGTH_LONG).show()
                }
            }
    }
}