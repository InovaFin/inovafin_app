package com.example.inovafin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityContaEscolhidaBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale

class ContaEscolhida : AppCompatActivity() {

    private lateinit var binding: ActivityContaEscolhidaBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContaEscolhidaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        var contaNome = intent.getStringExtra("contaNome")

        // Resgatar dados aqui!
        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document("$contaNome")
            .addSnapshotListener { document, error ->
                if (document != null) {
                    val saldoResgatado = document.getDouble("saldo")

                    val formatted = numberFormat.format(saldoResgatado)

                    // Aplica os dados no layout
                    binding.titulo.text = contaNome
                    binding.instituicao.text = "Saldo atual " + document.getString("instituicao")
                    binding.saldo.text = formatted
                } else {
                    Toast.makeText(applicationContext, "Erro ao resgatar", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {
            resgatarDados()
        }
    }
}