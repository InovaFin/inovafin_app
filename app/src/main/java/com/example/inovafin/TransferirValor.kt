package com.example.inovafin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Toast
import com.example.inovafin.Util.AnimacaoDeLoad
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

    private lateinit var animacaoDeLoad: AnimacaoDeLoad

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var contaId: String = ""
    private var saldoAtual: Double = 0.0

    private var nomeRemetente: String = ""
    private var nomeDestinatario: String = ""
    private var valorTransferido: String = ""

    private var contaAlterada: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransferirValorBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()
        animacaoDeLoad = AnimacaoDeLoad(binding.btAnimacao, binding.btText, this)

        contaId = intent.getStringExtra("contaId").toString()

        resgatarDados()
        resgatarContas()
        formatandoValor()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btTransferir.setOnClickListener {
            animacaoDeLoad.iniciarAnimacao()
            verificarConta()
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        try {
            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias").document(contaId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        val nomeResgatado = document.getString("nome").toString()

                        binding.nomeRemetente.text = nomeResgatado
                        nomeRemetente = nomeResgatado

                        val saldoResgatado = document.getDouble("saldo")

                        if (saldoResgatado != null) {
                            saldoAtual = saldoResgatado
                        }

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

    private fun formatandoValor() {
        val editTextMonetario = binding.valorTranferir

        // Defina o texto inicial como "R$ "
        editTextMonetario.setText("R$ 0,00")

        // Posicione o cursor no final do texto
        editTextMonetario.setSelection(editTextMonetario.text.length)

        // Adicionando o MoneyTextWatcher ao Saldo Atual
        editTextMonetario.addTextChangedListener(MoneyTextWatcher(editTextMonetario))
    }

    private fun parseDoubleValor(valor: String): Double {
        // Remova o símbolo da moeda, nesse caso "R$ "
        val valorSemSimbolo = valor.replace(NumberFormat.getCurrencyInstance().currency.symbol, "")

        // Remova todos os caracteres que não são dígitos (0-9), incluindo pontos e vírgulas
        val valorSemFormatacao = valorSemSimbolo.replace(Regex("[^\\d]"), "")

        return valorSemFormatacao.toDouble() / 100.0
    }

    private fun resgatarContas() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                val nomesContas = mutableListOf<String>()

                for (document in documents) {
                    val nomeConta = document.getString("nome")
                    if (nomeConta != null && nomeConta != nomeRemetente) {
                        nomesContas.add(nomeConta)
                    }
                }

                // Agora você tem a lista de nomes das contas bancárias
                configurarSpinner(nomesContas)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Erro: $exception", Toast.LENGTH_LONG).show()
            }
    }

    private fun configurarSpinner(nomesContas: MutableList<String>) {
        val spinnerOP = mutableListOf("Selecione uma conta")
        spinnerOP.addAll(nomesContas)

        // Cria um adapter para o spinner com a lista de nomes
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter
    }

    private fun verificarSpinner() {
        val valorSpinner = binding.spinner.selectedItem

        contaAlterada = valorSpinner != "Selecione uma conta"
    }

    private fun verificarConta() {
        val usuarioId = autentificacao.currentUser!!.uid

        verificarSpinner()

        if (contaAlterada) {
            nomeDestinatario = binding.spinner.selectedItem.toString()

            // Verificar conta aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .collection("ContasBancarias")
                .whereEqualTo("nome", nomeDestinatario)
                .get()
                .addOnSuccessListener { documents ->
                    // Documentos correspondentes foram encontrados
                    for (document in documents) {
                        transferirValor(document.id)
                    }
                }
                .addOnFailureListener { exception ->
                    Toast.makeText(applicationContext, "Erro ao verificar: $exception", Toast.LENGTH_LONG).show()
                }
        } else {
            animacaoDeLoad.pararAnimacao()
            Toast.makeText(applicationContext, "Selecione uma conta", Toast.LENGTH_LONG).show()
        }
    }

    private fun transferirValor(contaDestId: String) {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document(contaDestId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    var valorAtual = document.getDouble("saldo")
                    if (valorAtual != null) {
                        val valorTransferir = binding.valorTranferir.text.toString()
                        valorAtual += parseDoubleValor(valorTransferir)

                        animacaoDeLoad.pararAnimacao()

                        atualizarContaRemetente()
                        atualizarContaDestinatario(valorAtual, contaDestId)

                        val i = Intent(this, SplashTransferir::class.java)
                        valorTransferido = binding.valorTranferir.text.toString()
                        i.putExtra("nomeRemetente", nomeRemetente)
                        i.putExtra("nomeDestinatario", nomeDestinatario)
                        i.putExtra("valorTransferido", valorTransferido)
                        startActivity(i)
                    }
                }
            }
            .addOnFailureListener { exception ->
                animacaoDeLoad.pararAnimacao()
                Toast.makeText(applicationContext, "Erro ao buscar documento: $exception", Toast.LENGTH_LONG).show()
            }
    }

    private fun atualizarContaRemetente() {
        val usuarioId = autentificacao.currentUser!!.uid
        val valorTransferir = binding.valorTranferir.text.toString()

        val novoSaldo = saldoAtual - parseDoubleValor(valorTransferir)

        val usuarioMasp = hashMapOf(
            "saldo" to novoSaldo
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document(contaId)
            .update(usuarioMasp as Map<String, Any>)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                }
            }

    }

    private fun atualizarContaDestinatario(valorAtual: Double, contaDestId: String) {
        val usuarioId = autentificacao.currentUser!!.uid

        val usuarioMasp = hashMapOf(
            "saldo" to valorAtual
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document(contaDestId)
            .update(usuarioMasp as Map<String, Any>)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                }
            }
    }
}
