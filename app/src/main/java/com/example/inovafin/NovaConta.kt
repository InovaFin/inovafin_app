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

    private lateinit var binding: ActivityNovaContaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNovaContaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Criando o array com opções
        val spinnerOP = listOf(
            "Banco do Brasil", "Banco Inter", "Banco Safra", "Bradesco", "BTG Pactual",
            "Caixa", "C6 Bank", "Itaú", "Mercado Pago", "Nubank", "PagBank", "PicPay",
            "Santander", "Sicoob"
        )

        // Criando um adapter para o spinner
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter

    }
}