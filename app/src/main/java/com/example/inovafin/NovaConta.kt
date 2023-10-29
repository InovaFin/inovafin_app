package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.inovafin.databinding.ActivityNovaContaBinding
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class NovaConta : AppCompatActivity() {

    private lateinit var binding: ActivityNovaContaBinding

    private var numberFormat = NumberFormat.getCurrencyInstance()

    private var saldoAtual = 0.00

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaContaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        // Criando o array com opções
        val spinnerOP = listOf(
            "Banco do Brasil", "Banco Inter", "Banco Safra", "Bradesco", "BTG Pactual",
            "Caixa", "C6 Bank", "Itaú", "Mercado Pago", "Nubank", "PagBank", "PicPay",
            "Santander", "Sicoob"
        )

        // Criando um adapter para o spinner
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter

        val editTextMonetario = binding.saldoAtual

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText("R$ 0,00")

        // Posicione o cursor no final do texto
        editTextMonetario.setSelection(editTextMonetario.text.length)

        // Adicionando o MoneyTextWatcher ao Saldo Atual
        editTextMonetario.addTextChangedListener(MoneyTextWatcher(editTextMonetario))

        binding.btAlterarDados.setOnClickListener {
            validarCampos()
            // Exibir o saldo atual no TextView ou realizar outras ações aqui
            val saldoAtual = editTextMonetario.text.toString()
            Toast.makeText(applicationContext, "Saldo atual: $saldoAtual", Toast.LENGTH_LONG).show()
        }
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
            // Não é necessário implementar
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Não é necessário implementar
        }

    }

    private fun validarCampos() {
        if () {
            adicionarConta()
        }
    }

    private fun adicionarConta() {
        TODO("Not yet implemented")
    }
}
