package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    private var saldoGeral: Double = 0.0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySaldoGeralBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

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

        binding.exibirSaldo.setOnClickListener {
            resgatarDados()
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

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias")
            .get()
            .addOnSuccessListener { documents ->
                val nomesContas = mutableListOf<String>()
                var soma = 0.0

                for (document in documents) {
                    val nomeConta = document.getString("nome")
                    val saldoConta = document.getDouble("saldo")

                    if (nomeConta != null) {
                        nomesContas.add(nomeConta)
                    }

                    if (saldoConta != null) {
                        soma += saldoConta
                    }
                }

                saldoGeral = soma
                saldoGeralTemporario()

                // Agora você tem a lista de nomes das contas bancárias
//                configurarSpinner(nomesContas)

            }
            .addOnFailureListener { exception ->
                Toast.makeText(applicationContext, "Erro: $exception", Toast.LENGTH_LONG).show()
            }
    }

    private fun saldoGeralTemporario() {
        val usuarioId = autentificacao.currentUser!!.uid

        val registroMasp = hashMapOf(
            "saldoGeral" to saldoGeral
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("saldoGeralTemporario")
            .document()
            .set(registroMasp)
            .addOnSuccessListener {
                val formatted = numberFormat.format(saldoGeral)
                binding.saldoGeral.text = formatted

                binding.exibirSaldo.visibility = View.GONE
                binding.saldoGeral.visibility = View.VISIBLE
            }

    }
}