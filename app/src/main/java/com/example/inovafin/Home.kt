package com.example.inovafin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.inovafin.Util.ConfiguraBd
import com.example.inovafin.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.NumberFormat
import java.util.Locale

class Home : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var autentificacao: FirebaseAuth

    private lateinit var firestore: FirebaseFirestore

    private var numberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

    private var saldoGeral: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        autentificacao = ConfiguraBd.Firebaseautentificacao()
        firestore = ConfiguraBd.Firebasefirestore()

        binding.imagemUsuario.setOnClickListener {
            var i = Intent(this, Configuracoes::class.java)
            startActivity(i)
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

//        binding.btNotificacao.setOnClickListener {
//            var i = Intent(this, Notificacoes::class.java)
//            startActivity(i)
//        }

        binding.btSaldoGeral.setOnClickListener {
            var i = Intent(this, SaldoGeral::class.java)
            startActivity(i)
        }

        binding.btMinhasContas.setOnClickListener {
            var i = Intent(this, MinhasContas::class.java)
            startActivity(i)
        }

        binding.btValorReceber.setOnClickListener {
            var i = Intent(this, ValorReceber::class.java)
            startActivity(i)
        }

        binding.btValorPagar.setOnClickListener {
            val i = Intent(this, ContaPagar::class.java)
            startActivity(i)
        }

        binding.btValorGuardado.setOnClickListener {
            var i = Intent(this, ValorGuardado::class.java)
            startActivity(i)
        }
    }

    override fun onStart() {
        super.onStart()
        val usuarioAuth = autentificacao.currentUser

        if (usuarioAuth != null) {

            val usuarioId = autentificacao.currentUser!!.uid

            // Resgatar dados aqui!
            firestore.collection("Usuarios").document(usuarioId)
                .addSnapshotListener { document, error ->
                    if (document != null) {
                        binding.nomeUsuario.text = document.getString("nome")

                        val foto = document.getString("foto")
                        // Verifique se a foto do banco não é nula antes de carregar
                        if (!foto.isNullOrEmpty()) {
                            Glide.with(this).load(foto).into(binding.imagemUsuario)
                        } else {
                            binding.imagemUsuario.setImageURI(null)
                        }
                    }
                }

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

                    saldoGeral = soma

                    val formatted = numberFormat.format(soma)
                    binding.saldoGeral.text = formatted

                    aplicarSaldoGeral()
                }
        }
    }

    private fun aplicarSaldoGeral() {
        val usuarioId = autentificacao.currentUser!!.uid

        val registroMasp = hashMapOf(
            "saldoGeral" to saldoGeral
        )

        firestore.collection("Usuarios").document(usuarioId)
            .collection("saldoGeralTemporario")
            .document("temporario")
            .set(registroMasp as Map<String, Any>)
            .addOnSuccessListener {

            }
    }
}