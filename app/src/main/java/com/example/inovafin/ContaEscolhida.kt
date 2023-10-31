package com.example.inovafin

import android.content.Intent
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
    private var nomeConta: String = ""
    private var instituicao: String = ""
    private var saldoConta: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContaEscolhidaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        nomeConta = intent.getStringExtra("nomeConta").toString()

        binding.icFechar.setOnClickListener {
            onBackPressed()
        }

        binding.btEditar.setOnClickListener {
            val i = Intent(this, EditarConta::class.java)
            i.putExtra("nomeConta", nomeConta)
            i.putExtra("saldoConta", saldoConta)
            i.putExtra("instituicao", instituicao)
            startActivity(i)
        }
    }

    private fun resgatarDados() {
        val usuarioId = autentificacao.currentUser!!.uid

        // Resgatar dados aqui!
        firestore.collection("Usuarios").document(usuarioId)
            .collection("ContasBancarias").document("$nomeConta")
            .addSnapshotListener { document, error ->
                if (document != null) {
                    val saldoResgatado = document.getDouble("saldo")
                    val instituicaoResgatada = document.getString("instituicao")

                    val formatted = numberFormat.format(saldoResgatado)
                    saldoConta = formatted

                    instituicao = instituicaoResgatada.toString()

                    // Aplica os dados no layout
                    binding.titulo.text = nomeConta
                    binding.instituicao.text = "Saldo atual $instituicaoResgatada"
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