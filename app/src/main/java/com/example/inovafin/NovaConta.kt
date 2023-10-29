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
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityNovaContaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class NovaConta : AppCompatActivity() {

    private lateinit var binding: ActivityNovaContaBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance()

    private var nomeAlterado = false
    private var instituicaoAlterada = false
    private var saldoAlterado = false
    private var saldoAtualFinal: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaContaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        configurarTextWatcherNome()
        configurarTextWatcherSaldo()

        spinnerInstituicoes()
        formatandoSaldo()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btAdicionar.setOnClickListener {
            validarCampos()
        }
    }

    private fun configurarTextWatcherNome() {
        binding.nomeConta.addTextChangedListener(object : TextWatcher {
            private var nomeAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                nomeAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (nomeAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Nome foi preenchido pela primeira vez ou o texto foi reescrito
                    nomeAlterado = true
                } else if (!nomeAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Nome foi apagado
                    nomeAlterado = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
    }

    private fun configurarTextWatcherSaldo() {
        binding.saldoAtual.addTextChangedListener(object : TextWatcher {
            private var saldoAnterior: CharSequence? = null

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                saldoAnterior = s?.toString() // Salva o texto anterior
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Quando o texto está sendo alterado
                if (saldoAnterior.isNullOrEmpty() && !s.isNullOrEmpty()) {
                    // Saldo foi preenchido pela primeira vez ou o texto foi reescrito
                    val saldo = binding.saldoAtual.text
                    saldoAlterado = saldo.toString() != "R$ 0,00"
                } else if (!saldoAnterior.isNullOrEmpty() && s.isNullOrEmpty()) {
                    // Saldo foi apagado
                    saldoAlterado = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // Nada a fazer após a mudança
            }
        })
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

        if (valorSpinner == "Selecione uma opção") {
            instituicaoAlterada = false
        } else {
            instituicaoAlterada = true
        }
    }

    private fun formatandoSaldo() {
        val editTextMonetario = binding.saldoAtual

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText("R$ 0,00")

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

    private fun validarCampos() {
        verificarSaldo()
        verificarSpinner()
        if (nomeAlterado || saldoAlterado) {
            if (saldoAtualFinal > 0.0) {
                if (instituicaoAlterada) {
                    adicionarConta()
                } else {
                    Toast.makeText(applicationContext, "Selecione uma instituição", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(applicationContext, "Saldo inválido. O saldo deve ser maior que zero.", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(applicationContext, "Preencha todos os campos", Toast.LENGTH_LONG).show()
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

    private fun adicionarConta() {
        val usuarioId = autentificacao.currentUser!!.uid
        val nome = binding.nomeConta.text.toString()
        val instituicao = binding.spinner.selectedItem

        val usuarioMasp = hashMapOf(
            "nome" to nome,
            "instituicao" to instituicao,
            "saldo" to saldoAtualFinal
        )

        try {
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document()
                .set(usuarioMasp).addOnCompleteListener(this) {task ->
                    if (task.isSuccessful) {
                        val i = Intent(this, MinhasContas::class.java)
                        startActivity(i)
                    } else {
                        var excecao = ""

                        try {
                            throw task.exception!!
                        } catch (e: Exception) {
                            excecao = "Erro ao salvar conta" + e.message
                            e.printStackTrace()
                        }
                        Toast.makeText(applicationContext, "$excecao", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "$e", Toast.LENGTH_LONG).show()
        }
    }
}
