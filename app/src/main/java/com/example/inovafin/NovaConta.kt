package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.inovafin.databinding.ActivityNovaContaBinding
import com.koushikdutta.ion.Ion
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.math.BigDecimal
import java.net.HttpURLConnection
import java.net.URL
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

class NovaConta : AppCompatActivity() {

    var host = "https://inovafin.000webhostapp.com/projeto/inserirBanco.php";

    private lateinit var binding: ActivityNovaContaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Pegando o saldoConta
        //binding.saldoConta.addTextChangedListener(ExampleTextWatcher(binding.saldoConta))


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


        binding.btnConcluir.setOnClickListener {
            cadastroBanco();
        }
    }

    fun cadastroBanco() {
        val url = host

        var nomeBanco = binding.contaEditText.text.toString();
        var instituicao = binding.spinner.selectedItem.toString();
        var saldoConta = binding.saldoConta.text.toString();

        if (nomeBanco.isNotEmpty() && instituicao.isNotEmpty() && saldoConta.isNotEmpty()) {

            Ion.with(this)
                .load(url)
                .setBodyParameter("nomeBanco", nomeBanco)
                .setBodyParameter("instituicao", instituicao)
                .setBodyParameter("saldoConta", saldoConta)
                .asJsonObject()
                .setCallback { e, result ->
                    if (e == null) {

                        runOnUiThread {
                            Toast.makeText(
                                this,
                                "Operação bem-sucedida",
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                    } else {
                        runOnUiThread { Toast.makeText(this, "ERRO", Toast.LENGTH_SHORT).show() }
                    }

                }

        }
    }
}
/*class ExampleTextWatcher(val saldoConta: EditText) : TextWatcher {
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
}*/
