package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import com.example.inovafin.databinding.ActivityNovaContaBinding
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class NovaConta : AppCompatActivity() {

    private lateinit var binding: ActivityNovaContaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pegando o saldoConta
        binding.saldoConta.addTextChangedListener(ExampleTextWatcher(binding.saldoConta))

        // Criando o array com opções
        val spinnerOP = listOf(
            "Nubank", "Itaú", "Santander", "Bradesco", "C6 Bank", "PicPay",
            "Banco Inter", "Mercado Pago", "Banco do Brasil", "PagBank", "Caixa",
            "BTG Pactual", "Banco Safra"
        )

        // Criando um adapter para o spinner
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerOP)

        // Especificando o layout do dropdown
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1)

        // Conectando o adapter ao spinner
        binding.spinner.adapter = adapter

    }
}

class ExampleTextWatcher(val saldoConta: EditText) : TextWatcher {
    companion object {
        private const val replaceRegex: String = "[R$,.\u00A0]"
        private const val replaceFinal: String = "R$\u00A0"
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        // Implemente a lógica antes de o texto ser alterado (se necessário)
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        // Implemente a lógica quando o texto está sendo alterado (se necessário)
    }

    override fun afterTextChanged(editable: Editable?) {
        try {
            val stringEditable = editable.toString()
            if (stringEditable.isEmpty()) return
            saldoConta.removeTextChangedListener(this)
            val clearString = stringEditable.replace(replaceRegex.toRegex(), "")

            val parsed = BigDecimal(clearString)
                .setScale(2, BigDecimal.ROUND_FLOOR)
                .divide(BigDecimal(100), BigDecimal.ROUND_FLOOR)

            val decimalFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR")) as DecimalFormat
            val formatted = decimalFormat.format(parsed)

            val stringFinal = formatted.replace(replaceFinal, "")
            saldoConta.setText(stringFinal)
            saldoConta.setSelection(stringFinal.length)
            saldoConta.addTextChangedListener(this)
        } catch (e: Exception) {
            Log.e("ERRO", e.toString())
        }
    }
}