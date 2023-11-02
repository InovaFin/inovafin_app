package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityTransferirValorBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class TransferirValor : AppCompatActivity() {

    private lateinit var binding: ActivityTransferirValorBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var contaId: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferirValorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        contaId = intent.getStringExtra("contaId").toString()

        resgatarDados()

        formatandoSaldo()
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document(contaId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        val saldoResgatado = document.getDouble("saldo")

                        val formatted = numberFormat.format(saldoResgatado)

                        // Aplica os dados no layout
                        binding.saldo.text = formatted
                    } else {
                        Toast.makeText(applicationContext, "Erro ao resgatar", Toast.LENGTH_LONG).show()
                    }
                }
        } catch (e: Exception) {
            Toast.makeText(applicationContext, "Erro ao resgatar dados", Toast.LENGTH_LONG).show()
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

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // Não é necessário implementar
        }

    }

    private fun formatandoSaldo() {
        val editTextMonetario = binding.valorTranferir

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText("R$ 0,00")

        // Posicione o cursor no final do texto
        editTextMonetario.setSelection(editTextMonetario.text.length)

        // Adicionando o MoneyTextWatcher ao Saldo Atual
        editTextMonetario.addTextChangedListener(MoneyTextWatcher(editTextMonetario))
    }
}