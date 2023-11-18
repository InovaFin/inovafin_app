package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivitySaldoGeralBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale

class SaldoGeral : AppCompatActivity() {

    private lateinit var binding: ActivitySaldoGeralBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private lateinit var nomesContas: MutableList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        verificarSpinner()
        resgatarContas()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btCalculadora.setOnClickListener {
            try {
                val intent = Intent()
                intent.action = Intent.ACTION_MAIN
                intent.addCategory(Intent.CATEGORY_APP_CALCULATOR)
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Não foi possível abrir a calculadora.", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btAjuda.setOnClickListener {
            val i = Intent(this, AjudaSaldoGeral::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralReceber.setOnClickListener {
            val i = Intent(this, SaldoGeralReceber::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralGuardado.setOnClickListener {
            val i = Intent(this, SaldoGeralGuardado::class.java)
            startActivity(i)
        }

        binding.btSaldoGeralPagar.setOnClickListener {
            val i = Intent(this, SaldoGeralPagar::class.java)
            startActivity(i)
        }
    }

    private fun resgatarSaldoGeral() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("saldoGeralTemporario")
            .document("temporario")
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val saldo = document.getDouble("saldoGeral")
                    val formatted = numberFormat.format(saldo)

                    binding.saldoGeral.text = formatted
                }
            }
    }

    private fun resgatarContas() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                nomesContas = mutableListOf<String>()

                for (document in documents) {
                    val nomeConta = document.getString("nome")

                    if (nomeConta != null) {
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
        val spinnerOP = mutableListOf("Saldo Geral")
        spinnerOP.addAll(nomesContas)

        // Cria um adapter para o spinner com a lista de nomes
        val adapter = ArrayAdapter(this, R.layout.item_spinner_layout, spinnerOP)

        binding.spinner.adapter = adapter
    }

    private fun verificarSpinner() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                // Verifica se a posição selecionada não é a posição padrão "Selecione uma conta"
                if (position > 0) {
                    // Recupera o nome da conta selecionadaA
                    val contaSelecionada = nomesContas[position - 1]

                    // Aqui você pode adicionar o código para exibir o saldo da conta selecionada
                    resgatarContaSelecionada(contaSelecionada)
                } else if (position == 0) {
                    aplicarSaldoGeral()
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Aqui você pode adicionar o código para lidar com nenhum item selecionado, se necessário
            }
        }
    }

    private fun aplicarSaldoGeral() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                var soma = 0.0

                for (document in documents) {
                    val saldoConta = document.getDouble("saldo")

                    if (saldoConta != null) {
                        soma += saldoConta
                    }
                }

                val registroMasp = hashMapOf(
                    "saldoGeral" to soma
                )

                firestore.collection("Usuarios").document(usuarioId)
                    .collection("saldoGeralTemporario")
                    .document("temporario")
                    .set(registroMasp as Map<String, Any>)
                    .addOnSuccessListener {
                        resgatarSaldoGeral()
                    }
            }
    }

    private fun resgatarContaSelecionada(nomeConta: String) {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .whereEqualTo("nome", nomeConta)
            .get()
            .addOnSuccessListener { documents ->
                var saldo = 0.0

                for (document in documents) {
                    saldo = document.getDouble("saldo")!!
                }

                alterarSaldoGeral(saldo)
            }
    }

    private fun alterarSaldoGeral(saldo: Double) {
        val usuarioId = autentificacao.currentUser!!.uid

        val registroMasp = hashMapOf(
            "saldoGeral" to saldo
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("saldoGeralTemporario")
            .document("temporario")
            .update(registroMasp as Map<String, Any>)
            .addOnSuccessListener {
                resgatarSaldoGeral()
            }
    }

    override fun onStart() {
        super.onStart()

        resgatarSaldoGeral()
    }
}